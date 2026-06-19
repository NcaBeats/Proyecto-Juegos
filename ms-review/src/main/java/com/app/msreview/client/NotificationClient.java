package com.app.msreview.client;

import com.app.msreview.dto.external.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-notification")
public interface NotificationClient {
    @PostMapping("/api/v1/notifications")
    void createNotification(NotificationRequest request);
}