package com.app.exception.custom;

import java.time.Instant;

public record ErrorResponse(
        String message,
        int code,
        Instant timestamp
) {
}
