package com.app.mspurchase.purchase.dto;

import com.app.mspurchase.purchasegame.dto.PurchaseGameRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseRequest(
         Long userId,
         List<PurchaseGameRequest> juegos
) {
}
