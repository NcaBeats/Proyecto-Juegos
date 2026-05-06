package com.app.mswishlist.wishlistgame.mapper;

import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.stereotype.Component;

@Component
public class WishlistGameMapper {
    public WishlistGame toEntity (WishlistGameRequest request, Wishlist wishlist){
        return WishlistGame.builder()
                .gameId(request.gameId())
                .wishlist(wishlist)
                .build();
    }
    public WishlistGameResponse toResponse (WishlistGame entity, JuegoResponse juegoResponse){
        return WishlistGameResponse.builder()
                .id(entity.getId())
                .gameId(entity.getGameId())
                .gameName(juegoResponse.nombre())
                .price(juegoResponse.precio())
                .fechaLanzamiento(juegoResponse.fechaLanzamiento())
                .fechaCreacion(entity.getFechaCreacion())
                .build();

    }
}
