package com.app.msreview.client;

import com.app.msreview.dto.external.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-client", url = "http://localhost:8086/api/v1/notifications")
public interface NotificationClient {
    @PostMapping
    void createNotification(NotificationRequest request);
}