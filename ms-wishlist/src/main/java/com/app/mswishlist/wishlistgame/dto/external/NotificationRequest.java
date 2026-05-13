package com.app.mswishlist.wishlistgame.dto.external;

import com.app.mswishlist.wishlistgame.dto.external.enums.TipoNotification;
import lombok.Builder;

@Builder
public record NotificationRequest(
        Long userId,
        String message,
        TipoNotification tipo,
        Long gameId
) {
}
