package com.app.mswishlist.wishlist.mapper;

import com.app.mswishlist.wishlist.dto.WishlistRequest;
import com.app.mswishlist.wishlist.dto.WishlistResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {

    public Wishlist toEntity(WishlistRequest request) {
        return Wishlist.builder()
                .userId(request.userId())
                .build();
    }

    public WishlistResponse toResponse(Wishlist wishlist) {
        return WishlistResponse.builder()
                .userId(wishlist.getUserId())
                .fechaCreacion(wishlist.getFechaCreacion())
                .build();
    }
}