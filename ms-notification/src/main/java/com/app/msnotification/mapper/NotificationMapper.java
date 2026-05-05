package com.app.msnotification.mapper;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationRequest request) {
        return Notification.builder()
                .userId(request.userId())
                .message(request.message())
                .tipo(request.tipo())
                .gameId(request.gameId())
                .build();
    }
    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .message(notification.getMessage())
                .tipo(notification.getTipo())
                .gameId(notification.getGameId())
                .fechaCreacion(notification.getFechaCreacion())
                .build();
    }
}
