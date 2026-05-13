package com.example.mspurchase.purchase.dto;

import com.example.mspurchase.purchasegame.dto.PurchaseGameResponse;
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
