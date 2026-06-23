package com.app.msnotification.service;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.mapper.NotificationMapper;
import com.app.msnotification.model.Notification;
import com.app.msnotification.dto.PurchaseNotificationRequest;
import com.app.msnotification.model.TipoNotification;
import com.app.msnotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public Page<NotificationResponse> getAllByUserId(Long userId, Pageable pageable) {
        log.debug("Obteniendo notificaciones para userId={} página={} tamaño={}", userId, pageable.getPageNumber(), pageable.getPageSize());
        return notificationRepository.getAllByUserId(userId,pageable)
                .map(notificationMapper::toResponse);
    }
    @Transactional
    public NotificationResponse save (NotificationRequest notificationRequest) {
        log.info("Guardando notificación para userId={} tipo={}", notificationRequest.userId(), notificationRequest.tipo());
        Notification notification = notificationMapper.toEntity(notificationRequest);
        Notification saved = notificationRepository.save(notification);
        log.info("Notificación guardada con id={} userId={}", saved.getId(), saved.getUserId());
        return notificationMapper.toResponse(saved);
    }
    @Transactional
    public void savePurchaseNotification(PurchaseNotificationRequest request) {
        log.info("Creando notificaciones de compra para userId={} - {} juegos", request.userId(), request.juegos().size());

        request.juegos().stream()
                .map(juego -> Notification.builder()
                        .userId(request.userId())
                        .message("Compra: " + juego.name())
                        .tipo(TipoNotification.COMPRA)
                        .gameId(juego.id())
                        .build()
                )
                .forEach(notificationRepository::save);
        log.info("Notificaciones de compra creadas para userId={}", request.userId());
    }
}
