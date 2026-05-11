package com.example.mspurchase.purchasegame.dto.external;

import java.math.BigDecimal;

public record JuegoResponse(
        Long id,
        BigDecimal precio
){}
