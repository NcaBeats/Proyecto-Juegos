package com.app.msusuario.dto;

import lombok.Builder;

import java.time.Instant;
@Builder
public record UsuarioResponse (
        Long id,
        String nombre,
        String email,
        Instant fecha_registro
){
}
