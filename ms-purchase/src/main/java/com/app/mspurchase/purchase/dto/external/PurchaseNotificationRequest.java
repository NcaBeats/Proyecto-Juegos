package com.app.mspurchase.purchase.dto.external;

import com.app.mspurchase.purchase.dto.external.enums.TipoNotification;
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
