package com.example.mspurchase.dto;

import com.example.mspurchase.model.EstadoCompra;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record PurchaseResponse(
        Long id,
        Long juegoId,
        Long usuarioId,
        BigDecimal precio,
        EstadoCompra estado,
        Instant fechaRegistro,
        String nombreJuego,
        String nombreUsuario,
        String email
) {}
