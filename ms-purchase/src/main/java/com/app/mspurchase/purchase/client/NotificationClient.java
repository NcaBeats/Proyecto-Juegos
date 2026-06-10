package com.app.mspurchase.purchase.client;

import com.app.mspurchase.purchase.dto.external.PurchaseNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service-purchase", url = "http://localhost:8086/api/v1/notifications")
public interface NotificationClient {
    @PostMapping("/purchase")
    void createNotification(PurchaseNotificationRequest request);
}
