package com.app.mswishlist.wishlistgame.dto.external;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record JuegoResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        LocalDate fechaLanzamiento) {
}
