package com.app.mslibrary.librarygame.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LibraryGameRequest(
        @NotNull(message = "El campo gameId no puede ser nulo")
        Long gameId
) {
}
