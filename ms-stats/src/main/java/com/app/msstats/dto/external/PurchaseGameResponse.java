package com.app.msstats.dto.external;

import lombok.Builder;

@Builder
public record PurchaseGameResponse(
        Long userId,
        Long gameId,
        String gameName,
        int cantidad,
        double price
) {
}
