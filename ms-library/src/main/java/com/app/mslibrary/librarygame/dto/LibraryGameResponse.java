package com.app.mslibrary.librarygame.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record LibraryGameResponse(
        Long id,
        Long gameId,
        String gameName,
        Instant fechaCreacion
) {
}
