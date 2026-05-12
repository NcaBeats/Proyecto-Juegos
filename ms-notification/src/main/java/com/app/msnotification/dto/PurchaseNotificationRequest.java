package com.app.msnotification.dto;

import com.app.msnotification.dto.external.GamePurchaseResponse;
import com.app.msnotification.model.TipoNotification;
import lombok.Builder;
import java.util.List;

@Builder
public record PurchaseNotificationRequest(
        Long userId,
        String message,
        TipoNotification tipo,
        List<GamePurchaseResponse> juegos
) {
}
