package com.app.msstats.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GameStatsResponse(
        Long gameId,
        String gameName,
        int copiasVendidas,
        BigDecimal ventasTotales,
        double ratingPromedio
) {
}
