package com.app.mspurchase.purchasegame.dto;

import lombok.Builder;

@Builder
public record PurchaseGameRequest(
        Long gameId
) {
}
