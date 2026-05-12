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

        var games = wishlistService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist no encontrada"))
                .getGames()
                .stream()
                .map(wg -> wishlistGameMapper.toResponse(
                        wg,
                        juegoClient.getJuegoById(wg.getGameId())
                ))
                .toList();

        return WishListResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .games(games)
                .build();
    }

    @Transactional
    public WishlistGameResponse addGame(Long userId, WishlistGameRequest request) {

        if (wishlistGameRepository.existsByWishlistUserIdAndGameId(userId, request.gameId())) {
            throw new IllegalStateException("El juego ya está en la lista de deseos");
        }

        Wishlist wishlist = wishlistService.getOrCreate(userId);

        JuegoResponse juego = juegoClient.getJuegoById(request.gameId());

        WishlistGame wishlistGame =
                wishlistGameMapper.toEntity(request, wishlist);

        wishlistGameRepository.save(wishlistGame);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(userId)
                .message("Has añadido " + juego.nombre()+ " a tu Lista de Deseos")
                .tipo(TipoNotification.LISTA_DE_DESEOS)
                .gameId(request.gameId())
                .build();
        notificationClient.createNotification(notificationRequest);

        return wishlistGameMapper.toResponse(wishlistGame, juego);
    }

    @Transactional
    public void deleteGame(Long userId, Long gameId) {

        WishlistGame wishlistGame = wishlistGameRepository
                .findByWishlistUserIdAndGameId(userId, gameId)
                .orElseThrow(() ->
                        new IllegalStateException("El juego no está en la lista de deseos"));

        wishlistGameRepository.delete(wishlistGame);
    }
}