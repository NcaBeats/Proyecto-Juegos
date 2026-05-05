package com.app.msnotification.dto;

import com.app.msnotification.model.TipoNotification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NotificationRequest(
        @NotNull(message = "El campo userId no puede ser nulo")
        Long userId,
        @NotBlank(message = "El campo message no puede estar vacío")
        String message,
        @NotNull(message = "El campo tipo no puede ser nulo")
        TipoNotification tipo,
        @NotNull(message = "El campo gameId no puede ser nulo")
        Long gameId
) {
}
