package com.app.msstats.dto.external;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String name
) {
}
