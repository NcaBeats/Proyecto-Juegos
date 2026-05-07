package com.example.msreview.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ReviewResponse(
        Long id,
        Long userId,
        String nickname,
        String avatar,
        Long juegoId,
        String nombreJuego,
        String comentario,
        int rating,
        Instant fechaCreacion
) {
}
