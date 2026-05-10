package com.example.mspurchase.purchasegame.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record PurchaseFullResponse(
        Long purchaseId,
        String nickname,
        BigDecimal total,
        List<PurchaseGameResponse> games,
        Instant fecha
){}
