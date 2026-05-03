package com.app.mswishlist.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record WishlistResponse(
        Long userId,
        String nickname,
        Long gameId,
        String gameName,
        Instant fechaCreacion
) {
}