package com.example.mspurchase.purchase.dto.external;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserResponse(
        Long id,
        BigDecimal saldo
) {
}
