package com.app.mswishlist.wishlistgame.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname) {
}
