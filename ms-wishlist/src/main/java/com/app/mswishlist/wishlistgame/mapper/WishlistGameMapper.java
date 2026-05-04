package com.app.mswishlist.wishlistgame.mapper;

import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.stereotype.Component;

@Component
public class WishlistGameMapper {
    public WishlistGame toEntity (WishlistGameRequest request){
        return WishlistGame.builder()
                .userId(request.userId())
                .gameId(request.gameId())
                .build();
    }
    public WishlistGameResponse toResponse (WishlistGame entity, JuegoResponse juegoResponse, ProfileResponse profileResponse){
        return WishlistGameResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .nickname(profileResponse.nickname())
                .gameId(entity.getGameId())
                .gameName(juegoResponse.nombre())
                .price(juegoResponse.precio())
                .fechaLanzamiento(juegoResponse.fechaLanzamiento())
                .fechaCreacion(entity.getFechaCreacion())
                .build();

    }
}
