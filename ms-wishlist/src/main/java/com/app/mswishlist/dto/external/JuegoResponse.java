package com.app.mswishlist.dto.external;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String nombre
) {
}
