package com.example.msreview.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname,
        String avatar
) {
}
