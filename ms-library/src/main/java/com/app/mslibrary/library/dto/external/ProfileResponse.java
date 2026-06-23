package com.app.mslibrary.library.dto.external;

import lombok.Builder;

@Builder
public record ProfileResponse(
        Long userId,
        String nickname
) {
}
