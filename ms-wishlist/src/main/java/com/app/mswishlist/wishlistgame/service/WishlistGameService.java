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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistGameService {

    private final WishlistService wishlistService;
    private final WishlistGameRepository wishlistGameRepository;
    private final WishlistGameMapper wishlistGameMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;
    private final NotificationClient notificationClient;

    public WishListResponse getAllByUserId(Long userId) {

        ProfileResponse profile = profileClient.getProfileByUserId(userId);

        Set<WishlistGame> wishlistGames = wishlistGameRepository.getAllByWishlistUserId(userId);

        Set<WishlistGameResponse> games = wishlistGames
                .stream()
                .map(wg -> wishlistGameMapper.toResponse(
                        wg,
                        juegoClient.getJuegoById(wg.getGameId())
                ))
                .collect(Collectors.toSet());

        return WishListResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .games(games)
                .build();
    }

    @Transactional
    public WishlistGameResponse addGame(Long userId, WishlistGameRequest request) {

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
        }
        catch (Exception e) {
            System.err.println("Error al enviar la notificación: " + e.getMessage());
        }


        return wishlistGameMapper.toResponse(wishlistGame, juego);
    }

    @Transactional
    public void deleteGame(Long userId, Long gameId) {

        WishlistGame wishlistGame = wishlistGameRepository
                .findByWishlistUserIdAndGameId(userId, gameId)
                .orElseThrow(() ->
                        new EntityNotFoundException("El juego no está en la lista de deseos"));

        wishlistGameRepository.delete(wishlistGame);
    }
}