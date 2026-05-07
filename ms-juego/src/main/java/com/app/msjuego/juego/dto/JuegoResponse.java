package com.app.msjuego.juego.dto;

import com.app.msjuego.juego.model.EstadoJuego;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Builder
public record JuegoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        LocalDate fechaLanzamiento,
        EstadoJuego estado,
        Instant fecha_registro
) {
}
