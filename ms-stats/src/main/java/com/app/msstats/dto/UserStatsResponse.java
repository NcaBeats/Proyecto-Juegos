package com.app.msstats.dto;

import lombok.Builder;

@Builder
public record UserStatsResponse(
        Long userId,
        String nickname,
        String avatar,
        long juegosComprados,
        double dineroGastado,
        double promedioRatingDado
) {}