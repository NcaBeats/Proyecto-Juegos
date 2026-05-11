package com.example.msreview.dto.external;

import com.example.msreview.model.TipoNotification;
import lombok.Builder;

@Builder
public record NotificationRequest(
        Long userId,
        String message,
        TipoNotification tipo,
        Long gameId
) {
}