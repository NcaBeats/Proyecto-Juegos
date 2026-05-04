package com.app.mswishlist.wishlist.dto.external;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String nombre
) {
}
