package com.app.msusuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UsuarioRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @NotBlank(message = "El email no puede estar vacío")
        String email,
        @PositiveOrZero(message = "El saldo debe ser un valor positivo o cero")
        @NotNull(message = "El saldo no puede ser nulo")
        BigDecimal saldo
)
 {
}
