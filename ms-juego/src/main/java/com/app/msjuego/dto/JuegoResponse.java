package com.app.msjuego.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record JuegoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Instant fecha_registro
) {
}
