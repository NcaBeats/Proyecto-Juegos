package com.example.mslibrary.librarygame.dto.external;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String nombre
) {
}
