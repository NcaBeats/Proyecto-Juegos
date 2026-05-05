package com.app.msnotification.dto;

import com.app.msnotification.model.TipoNotification;
import lombok.Builder;

import java.time.Instant;

@Builder
public record NotificationResponse(
        Long id,
        Long userId,
        String message,
        TipoNotification tipo,
        Long gameId,
        Instant fechaCreacion) {
}
