package com.app.mswishlist.wishlistgame.client;

import com.app.mswishlist.wishlistgame.dto.external.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-client-wishlist", url = "http://localhost:8086/api/v1/notifications")
public interface NotificationClient {
    @PostMapping
    void createNotification(NotificationRequest request);
}
