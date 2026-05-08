package com.app.msjuego.estudio.dto;

import lombok.Builder;

@Builder
public record EstudioRequest(
        String nombre
) {
}
