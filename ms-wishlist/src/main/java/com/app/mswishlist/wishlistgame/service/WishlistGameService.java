package com.app.mswishlist.wishlistgame.service;


import com.app.mswishlist.wishlist.client.ProfileClient;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlist.service.WishlistService;
import com.app.mswishlist.wishlistgame.client.JuegoClient;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import com.app.mswishlist.wishlistgame.repository.WishlistGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistGameService {
    private final WishlistService wishlistService;
    private final WishlistGameRepository wishlistGameRepository;
    private final WishlistGameMapper wishlistGameMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;


    public WishListResponse getAllByUserId(Long userId) {
        ProfileResponse profile = profileClient.getProfileByUserId(userId);

        List<WishlistGame> wishlistGames = wishlistGameRepository.getAllByWishlistUserId(userId);
        List<WishlistGameResponse> games = wishlistGames.stream().map(wishlistGame -> {
        JuegoResponse juego = juegoClient.getJuegoById(wishlistGame.getGameId());
        return wishlistGameMapper.toResponse(wishlistGame, juego);
        }).toList();
        return WishListResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .games(games)
                .build();
    }
    @Transactional
    public WishlistGameResponse addGame (Long userId, WishlistGameRequest  wishlistGameRequest){
        Wishlist wishlist = wishlistService.getOrCreate(userId);
        if (wishlistGameRepository.existsByWishlistUserIdAndGameId(userId, wishlistGameRequest.gameId())) {
            throw new IllegalStateException("El juego ya está en la lista de deseos");
        }
        JuegoResponse juego = juegoClient.getJuegoById(wishlistGameRequest.gameId());
        WishlistGame wishlistGame = wishlistGameMapper.toEntity(wishlistGameRequest,wishlist);
        WishlistGame saved = wishlistGameRepository.save(wishlistGame);
        return wishlistGameMapper.toResponse(saved,juego);
    }
    @Transactional
    public void deleteGame (Long userId, Long gameId) {
        if (!wishlistGameRepository.existsByWishlistUserIdAndGameId(userId,gameId)) {
            throw new IllegalStateException("El juego no está en la lista de deseos");
        }
        wishlistGameRepository.deleteByWishlistUserIdAndGameId(userId,gameId);
    }
}
