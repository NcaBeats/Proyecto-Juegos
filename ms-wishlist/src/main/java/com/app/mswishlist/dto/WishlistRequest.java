package com.app.mswishlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WishlistRequest(
        @NotNull(message = "El campo userId no puede ser nulo")
        Long userId,
        @NotNull(message = "El campo gameId no puede ser nulo")
        Long gameId
) {
}