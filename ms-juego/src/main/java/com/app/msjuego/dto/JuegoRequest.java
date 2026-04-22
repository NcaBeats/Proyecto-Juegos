package com.app.msjuego.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record JuegoRequest(
        @NotBlank(message = "El nombre del juego es obligatorio")
        @Size(max = 50, message = "El nombre del juego no puede tener más de 50 caracteres")
        String nombre
) {
}
