package com.app.mswishlist.wishlistgame.service;

import com.app.mswishlist.wishlistgame.client.JuegoClient;
import com.app.mswishlist.wishlistgame.client.ProfileClient;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import com.app.mswishlist.wishlistgame.repository.WishlistGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistGameService {
    private final WishlistGameRepository wishlistGameRepository;
    private final WishlistGameMapper wishlistGameMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;

    public Page<WishlistGameResponse> getAllByUserId (Long userId, Pageable pageable) {
        ProfileResponse profile = profileClient.getProfileByUserId(userId);
        return wishlistGameRepository.getAllByUserId(userId,pageable).map(wishlistGame -> {
            JuegoResponse juego = juegoClient.getJuegoById(wishlistGame.getGameId());
            return wishlistGameMapper.toResponse(wishlistGame,juego,profile);
        });
    }
    public WishlistGameResponse addGame (WishlistGameRequest  wishlistGameRequest){
        if (wishlistGameRepository.existsByUserIdAndGameId(wishlistGameRequest.userId(), wishlistGameRequest.gameId())) {
            throw new IllegalStateException("El juego ya está en la lista de deseos");
        }
        WishlistGame saved = wishlistGameRepository.save(wishlistGameMapper.toEntity(wishlistGameRequest));
        JuegoResponse juego = juegoClient.getJuegoById(wishlistGameRequest.gameId());
        ProfileResponse profile = profileClient.getProfileByUserId(wishlistGameRequest.userId());
        return wishlistGameMapper.toResponse(saved,juego,profile);
    }
    public void deleteGame (WishlistGameRequest  wishlistGameRequest){
        if (!wishlistGameRepository.existsByUserIdAndGameId(wishlistGameRequest.userId(), wishlistGameRequest.gameId())) {
            throw new IllegalStateException("El juego no está en la lista de deseos");
        }
        wishlistGameRepository.deleteByUserIdAndGameId(wishlistGameRequest.userId(),wishlistGameRequest.gameId());
    }
}
