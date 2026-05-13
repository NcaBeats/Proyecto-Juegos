package com.app.mswishlist.wishlistgame.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Builder
public record WishlistGameResponse(
        Long id,
        Long gameId,
        String gameName,
        BigDecimal price,
        LocalDate fechaLanzamiento,
        Instant fechaCreacion) {
}
