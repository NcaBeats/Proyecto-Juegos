package com.app.mswishlist.wishlistgame.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WishlistGameRequest(
        @NotNull(message = "El campo gameId no puede ser nulo")
        Long gameId)
{
}
