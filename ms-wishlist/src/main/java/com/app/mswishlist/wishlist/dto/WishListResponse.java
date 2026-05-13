package com.app.mswishlist.wishlist.dto;

import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record WishListResponse(
        Long userId,
        String nickname,
        Set<WishlistGameResponse> games
) {
}
