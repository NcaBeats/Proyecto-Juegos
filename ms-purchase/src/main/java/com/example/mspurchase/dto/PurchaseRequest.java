package com.example.mspurchase.dto;

import com.example.mspurchase.model.EstadoCompra;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PurchaseRequest(
        @NotNull(message = "El ID del juego es obligatorio")
        Long juegoId,
        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,
        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio debe ser 0 o superior")
        BigDecimal precio,
        @NotNull(message = "El estado de la compra es obligatoria")
        EstadoCompra estado

) {}
