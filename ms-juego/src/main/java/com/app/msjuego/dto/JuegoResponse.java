package com.app.msjuego.dto;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String nombre
) {
}
