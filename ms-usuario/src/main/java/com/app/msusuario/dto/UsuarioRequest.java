package com.app.msusuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record UsuarioRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,
        @NotBlank(message = "El email no puede estar vacío")
        String email
)
 {
}
