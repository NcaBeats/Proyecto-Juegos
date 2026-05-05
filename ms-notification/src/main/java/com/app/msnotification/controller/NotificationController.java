package com.app.msnotification.controller;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<NotificationResponse>> getAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getAllByUserId(userId, pageable));
    }
    @PostMapping
    public ResponseEntity<NotificationResponse> save(@Valid @RequestBody NotificationRequest notificationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.save(notificationRequest));
    }
}

