package com.app.mswishlist.wishlist.dto;

import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record WishListResponse(
        Long userId,
        String nickname,
        List<WishlistGameResponse> games
) {
}
