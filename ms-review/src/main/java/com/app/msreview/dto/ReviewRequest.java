package com.app.msreview.dto;

import com.app.msreview.model.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @NotNull(message = "El campo userId no puede ser nulo")
        Long userId,
        @NotNull(message = "El campo juegoId no puede ser nulo")
        Long juegoId,
        @NotBlank(message = "El campo comentario no puede estar vacío")
        String comentario,
        @NotNull(message = "El campo rating no puede ser nulo")
        Rating rating
) {
}
