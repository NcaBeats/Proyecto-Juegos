package com.example.mslibrary.library.dto;

import com.example.mslibrary.librarygame.model.LibraryGame;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record LibraryResponse(
        Long userId,
        Set<LibraryGame> games,
        Instant fechaCreacion
) {
}
