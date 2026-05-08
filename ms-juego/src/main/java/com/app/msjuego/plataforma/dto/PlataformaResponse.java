package com.app.msjuego.plataforma.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PlataformaResponse(
        Long id,
        String nombre,
        Instant fechaCreacion
) {
}
