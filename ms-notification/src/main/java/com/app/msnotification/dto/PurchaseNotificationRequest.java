package com.app.msnotification.dto;

import com.app.msnotification.dto.external.GamePurchaseResponse;
import com.app.msnotification.model.TipoNotification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.List;

@Builder
public record PurchaseNotificationRequest(
        @NotNull(message = "El userId no puede ser nulo")
        Long userId,
        @NotBlank(message = "El mensaje no puede estar vacío")
        String message,
        @NotNull(message = "El tipo de notificación no puede ser nulo")
        TipoNotification tipo,
        @NotNull(message = "Los juegos no pueden ser nulos")
        List<GamePurchaseResponse> juegos
) {
}
