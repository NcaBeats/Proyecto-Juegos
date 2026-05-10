package com.app.msjuego.genero.dto;

import lombok.Builder;

@Builder
public record GeneroRequest(
        String nombre
) {
}
