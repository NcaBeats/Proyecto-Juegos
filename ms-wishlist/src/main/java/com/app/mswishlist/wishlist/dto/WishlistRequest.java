package com.app.mswishlist.wishlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WishlistRequest(
        @NotNull(message = "El campo userId no puede ser nulo")
        Long userId
) {
}