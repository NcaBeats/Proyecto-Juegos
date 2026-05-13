package com.example.mspurchase.purchasegame.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PurchaseGameStatsResponse(
        Long userId,
        Long gameId,
        String gameName,
        int cantidad,
        BigDecimal price
) {}