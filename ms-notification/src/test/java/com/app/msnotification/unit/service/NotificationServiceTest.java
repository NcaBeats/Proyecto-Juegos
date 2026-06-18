package com.app.msnotification.unit.service;

import com.app.msnotification.dto.PurchaseNotificationRequest;
import com.app.msnotification.dto.external.GamePurchaseResponse;
import com.app.msnotification.mapper.NotificationMapper;
import com.app.msnotification.model.Notification;
import com.app.msnotification.repository.NotificationRepository;
import com.app.msnotification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.msnotification.support.NotificationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    NotificationMapper notificationMapper;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void getAllByUserId_ReturnPage() {
        Notification notification = createNotificationEntity();
        Pageable pageable = Pageable.ofSize(10);
        Page<Notification> page = new PageImpl<>(List.of(notification));

        when(notificationRepository.getAllByUserId(USER_ID, pageable)).thenReturn(page);
        when(notificationMapper.toResponse(notification)).thenReturn(NOTIFICATION_RESPONSE);

        var result = notificationService.getAllByUserId(USER_ID, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(NOTIFICATION_RESPONSE, result.getContent().getFirst());
        verify(notificationRepository).getAllByUserId(USER_ID, pageable);
    }

    @Test
    void getAllByUserId_EmptyPage() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Notification> page = Page.empty();

        when(notificationRepository.getAllByUserId(USER_ID, pageable)).thenReturn(page);

        var result = notificationService.getAllByUserId(USER_ID, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(notificationRepository).getAllByUserId(USER_ID, pageable);
        verifyNoInteractions(notificationMapper);
    }

    @Test
    void save_Success() {
        Notification notification = createNotificationEntity();

        when(notificationMapper.toEntity(NOTIFICATION_REQUEST)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.toResponse(notification)).thenReturn(NOTIFICATION_RESPONSE);

        var result = notificationService.save(NOTIFICATION_REQUEST);

        assertNotNull(result);
        assertEquals(NOTIFICATION_RESPONSE, result);
        verify(notificationMapper).toEntity(NOTIFICATION_REQUEST);
        verify(notificationRepository).save(notification);
        verify(notificationMapper).toResponse(notification);
    }

    @Test
    void savePurchaseNotification_Success() {
        notificationService.savePurchaseNotification(PURCHASE_NOTIFICATION_REQUEST);

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void savePurchaseNotification_MultipleGames() {
        GamePurchaseResponse game1 = createGamePurchaseResponseFaker();
        GamePurchaseResponse game2 = createGamePurchaseResponseFaker();
        PurchaseNotificationRequest request = PurchaseNotificationRequest.builder()
                .userId(USER_ID)
                .message("Compra realizada")
                .tipo(TIPO)
                .juegos(List.of(game1, game2))
                .build();

        notificationService.savePurchaseNotification(request);

        verify(notificationRepository, times(2)).save(any(Notification.class));
    }

    @Test
    void savePurchaseNotification_EmptyGameList() {
        PurchaseNotificationRequest request = PurchaseNotificationRequest.builder()
                .userId(USER_ID)
                .message("Compra realizada")
                .tipo(TIPO)
                .juegos(List.of())
                .build();

        notificationService.savePurchaseNotification(request);

        verify(notificationRepository, never()).save(any(Notification.class));
    }
}
