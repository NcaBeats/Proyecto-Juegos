package com.app.msstats.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserStatsResponse(
        Long userId,
        String nickname,
        String avatar,
        int juegosComprados,
        BigDecimal dineroGastado,
        double promedioRatingDado
) {}