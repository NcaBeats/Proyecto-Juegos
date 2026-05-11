package com.example.mspurchase.purchasegame.dto;

import lombok.Builder;
import java.time.Instant;

@Builder
public record PurchaseGameResponse(
        Long id,
        Long gameId,
        Instant fechaRegistro
){}
