package com.app.mswishlist.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname) {
}
