package com.app.mswishlist.wishlistgame.service;

import com.app.mswishlist.wishlist.client.ProfileClient;
import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlist.service.WishlistService;
import com.app.mswishlist.wishlistgame.client.JuegoClient;
import com.app.mswishlist.wishlistgame.client.NotificationClient;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.dto.external.NotificationRequest;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlistgame.dto.external.enums.TipoNotification;
import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import com.app.mswishlist.wishlistgame.repository.WishlistGameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WishlistGameService {

    private final WishlistService wishlistService;
    private final WishlistGameRepository wishlistGameRepository;
    private final WishlistGameMapper wishlistGameMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;
    private final NotificationClient notificationClient;

    public WishListResponse getAllByUserId(Long userId) {
        log.debug("Obteniendo wishlist para userId={}", userId);
        log.debug("Llamando a ProfileClient.getProfileByUserId userId={}", userId);
        ProfileResponse profile = profileClient.getProfileByUserId(userId);

        Set<WishlistGame> wishlistGames = wishlistGameRepository.getAllByWishlistUserId(userId);

        Set<WishlistGameResponse> games = wishlistGames
                .stream()
                .map(wg -> {
                    log.debug("Llamando a JuegoClient.getJuegoById gameId={}", wg.getGameId());
                    JuegoResponse jr = juegoClient.getJuegoById(wg.getGameId());
                    return wishlistGameMapper.toResponse(wg, jr);
                })
                .collect(Collectors.toSet());
        log.debug("Wishlist obtenida userId={} juegos={}", userId, games.size());

        return WishListResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .games(games)
                .build();
    }

    @Transactional
    public WishlistGameResponse addGame(Long userId, WishlistGameRequest request) {
        log.info("Añadiendo juego a wishlist userId={} gameId={}", userId, request.gameId());
        Wishlist wishlist = wishlistService.getOrCreate(userId);

        if (wishlistGameRepository.existsByWishlistUserIdAndGameId(userId, request.gameId())) {
            throw new IllegalStateException("El juego ya está en la lista de deseos");
        }

        JuegoResponse juego = juegoClient.getJuegoById(request.gameId());

        WishlistGame wishlistGame = wishlistGameMapper.toEntity(request, wishlist);

        wishlistGameRepository.save(wishlistGame);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(userId)
                .message("Has añadido " + juego.nombre()+ " a tu Lista de Deseos")
                .tipo(TipoNotification.LISTA_DE_DESEOS)
                .gameId(request.gameId())
                .build();
        try {
            notificationClient.createNotification(notificationRequest);
            log.info("Notificación de wishlist enviada userId={} gameId={}", userId, request.gameId());
        }
        catch (Exception e) {
            log.warn("Error al enviar la notificación de wishlist userId={} gameId={}: {}", userId, request.gameId(), e.getMessage());
        }


        return wishlistGameMapper.toResponse(wishlistGame, juego);
    }

    @Transactional
    public void deleteGame(Long userId, Long gameId) {
        log.info("Eliminando juego de wishlist userId={} gameId={}", userId, gameId);
        WishlistGame wishlistGame = wishlistGameRepository
                .findByWishlistUserIdAndGameId(userId, gameId)
                .orElseThrow(() ->
                        new EntityNotFoundException("El juego no está en la lista de deseos"));

        wishlistGameRepository.delete(wishlistGame);
        log.info("Juego eliminado de wishlist userId={} gameId={}", userId, gameId);
    }
}
