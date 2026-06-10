package com.app.msreview.dto.external;

import lombok.Builder;

@Builder
public record JuegoResponse(
        Long id,
        String nombre
) {
}
