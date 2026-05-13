package com.app.msstats.dto.external;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        Long userId,
        int rating
) {
}
