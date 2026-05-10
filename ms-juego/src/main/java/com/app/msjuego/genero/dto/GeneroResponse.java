package com.app.msjuego.genero.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record GeneroResponse(
        Long id,
        String nombre,
        Instant fechaCreacion
) {
}
