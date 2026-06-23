package com.app.mswishlist.wishlistgame.client;

import com.app.mswishlist.wishlistgame.dto.external.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-notification")
public interface NotificationClient {
    @PostMapping("/api/v1/notifications")
    void createNotification(NotificationRequest request);
}
