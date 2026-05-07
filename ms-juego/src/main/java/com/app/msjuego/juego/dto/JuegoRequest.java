package com.app.msjuego.juego.dto;

import com.app.msjuego.juego.model.EstadoJuego;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record JuegoRequest(
        @NotBlank(message = "El nombre del juego es obligatorio")
        @Size(max = 50, message = "El nombre del juego no puede tener más de 50 caracteres")
        String nombre,
        @NotBlank(message = "La descripción no puede estar vacía")
        String descripcion,
        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio debe ser 0 o superior")
        BigDecimal precio,
        @NotNull(message = "La fecha de lanzamiento es obligatoria")
        LocalDate fechaLanzamiento,
        @NotBlank(message = "El estado es obligatorio")
        EstadoJuego estado
) {
}
