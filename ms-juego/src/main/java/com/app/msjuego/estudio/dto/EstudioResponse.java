package com.app.msjuego.estudio.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record EstudioResponse(
        Long id,
        String nombre,
        Instant fechaCreacion
) {
}
