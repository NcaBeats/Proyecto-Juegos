package com.example.mspurchase.purchase.dto;

import com.example.mspurchase.purchasegame.dto.PurchaseGameRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseRequest(
         Long userId,
         List<PurchaseGameRequest> juegos
) {
}
