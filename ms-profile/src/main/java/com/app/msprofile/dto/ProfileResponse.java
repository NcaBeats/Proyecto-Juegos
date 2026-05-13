package com.app.msprofile.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname,
        String avatar,
        String bio,
        Instant fecha_registro,
        String username,
        String email
) {
}
