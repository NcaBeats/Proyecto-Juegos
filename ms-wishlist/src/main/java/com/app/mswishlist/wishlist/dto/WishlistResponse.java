package com.app.mswishlist.wishlist.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record WishlistResponse(
        Long userId,
        Instant fechaCreacion
) {
}