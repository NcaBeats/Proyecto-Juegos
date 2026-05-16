package com.app.msnotification.service;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.mapper.NotificationMapper;
import com.app.msnotification.model.Notification;
import com.app.msnotification.dto.PurchaseNotificationRequest;
import com.app.msnotification.model.TipoNotification;
import com.app.msnotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public Page<NotificationResponse> getAllByUserId(Long userId, Pageable pageable) {
        return notificationRepository.getAllByUserId(userId,pageable)
                .map(notificationMapper::toResponse);
    }
    @Transactional
    public NotificationResponse save (NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }
    @Transactional
    public void savePurchaseNotification(PurchaseNotificationRequest request) {

        request.juegos().stream()
                .map(juego -> Notification.builder()
                        .userId(request.userId())
                        .message("Compra: " + juego.name())
                        .tipo(TipoNotification.COMPRA)
                        .gameId(juego.id())
                        .build()
                )
                .forEach(notificationRepository::save);
    }
}
