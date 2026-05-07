package com.app.msstats.dto;

import lombok.Builder;

@Builder
public record GameStatsResponse(
        Long gameId,
        String gameName,
        long ventasTotales,
        double ratingPromedio
) {
}
