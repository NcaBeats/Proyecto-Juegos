package com.app.mspurchase.purchase.dto;

import com.app.mspurchase.purchasegame.dto.PurchaseGameResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record PurchaseResponse(
         Long id,
         Long userId,
         BigDecimal totalPrecio,
         Instant fechaCompra,
         List<PurchaseGameResponse> juegos
) {
}
