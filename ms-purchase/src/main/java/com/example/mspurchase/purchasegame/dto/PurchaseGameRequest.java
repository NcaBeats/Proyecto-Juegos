package com.example.mspurchase.purchasegame.dto;

import lombok.Builder;

@Builder
public record PurchaseGameRequest(
        Long gameId
) {
}
