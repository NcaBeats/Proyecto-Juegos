package com.app.msstats.dto.external;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PurchaseGameResponse(
        Long userId,
        Long gameId,
        String gameName,
        int cantidad,
        BigDecimal price
) {
}
