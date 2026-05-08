package com.app.msjuego.plataforma.dto;

import lombok.Builder;

@Builder
public record PlataformaRequest(
        String nombre
) {
}
