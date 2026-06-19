package com.app.mspurchase.purchase.client;

import com.app.mspurchase.purchase.dto.external.PurchaseNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-notification")
public interface NotificationClient {
    @PostMapping("/api/v1/notifications/purchase")
    void createNotification(PurchaseNotificationRequest request);
}
