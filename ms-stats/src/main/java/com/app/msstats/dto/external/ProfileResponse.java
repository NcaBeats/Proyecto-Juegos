package com.app.msstats.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname,
        String avatar) {
}
