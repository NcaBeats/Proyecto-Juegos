package com.example.msreview.dto;

import com.example.msreview.model.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        Rating rating,
        Instant fechaCreacion
) {
}
