package com.app.msprofile.dto.external;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String nombre,
        String email
) {
}
