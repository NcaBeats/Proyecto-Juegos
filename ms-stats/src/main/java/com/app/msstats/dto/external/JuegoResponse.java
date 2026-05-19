package com.app.msstats.dto.external;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record JuegoResponse(
        Long id,
        String nombre,
        BigDecimal precio
) {
}
