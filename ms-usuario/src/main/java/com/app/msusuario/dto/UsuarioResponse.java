package com.app.msusuario.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
@Builder
public record UsuarioResponse (
        Long id,
        String nombre,
        String email,
        BigDecimal saldo,
        Instant fecha_registro
){
}
