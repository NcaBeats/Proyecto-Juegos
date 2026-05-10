package com.example.mspurchase.purchasegame.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record PurchaseGameResponse(
        Long id,
        Long gameId,
        String gameName,
        BigDecimal price
){}
