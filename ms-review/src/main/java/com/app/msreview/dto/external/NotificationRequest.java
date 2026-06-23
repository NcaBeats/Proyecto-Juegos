package com.app.msreview.dto.external;

import com.app.msreview.dto.external.enums.TipoNotification;
import lombok.Builder;

@Builder
public record NotificationRequest(
        Long userId,
        String message,
        TipoNotification tipo,
        Long gameId
) {
}