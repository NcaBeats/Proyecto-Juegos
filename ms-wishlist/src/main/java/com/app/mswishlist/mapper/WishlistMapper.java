package com.app.mswishlist.mapper;

import com.app.mswishlist.dto.WishlistRequest;
import com.app.mswishlist.dto.WishlistResponse;
import com.app.mswishlist.dto.external.JuegoResponse;
import com.app.mswishlist.dto.external.ProfileResponse;
import com.app.mswishlist.model.Wishlist;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {

    public Wishlist toEntity(WishlistRequest request) {
        return Wishlist.builder()
                .userId(request.userId())
                .gameId(request.gameId())
                .build();
    }

    public WishlistResponse toResponse(Wishlist wishlist, ProfileResponse profileResponse, JuegoResponse juegoResponse) {
        return WishlistResponse.builder()
                .userId(wishlist.getUserId())
                .nickname(profileResponse.nickname())
                .gameId(wishlist.getGameId())
                .gameName(juegoResponse.nombre())
                .fechaCreacion(wishlist.getFechaCreacion())
                .build();
    }
}