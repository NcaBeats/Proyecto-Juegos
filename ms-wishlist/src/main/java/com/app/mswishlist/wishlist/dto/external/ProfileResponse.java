package com.app.mswishlist.wishlist.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname) {
}
