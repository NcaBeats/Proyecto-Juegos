package com.app.msnotification.support;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.dto.PurchaseNotificationRequest;
import com.app.msnotification.dto.external.GamePurchaseResponse;
import com.app.msnotification.model.Notification;
import com.app.msnotification.model.TipoNotification;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class NotificationFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long ID = 1L;
    public static final Long USER_ID = 1L;
    public static final String MESSAGE = "Mensaje de prueba";
    public static final TipoNotification TIPO = TipoNotification.COMPRA;
    public static final Long GAME_ID = 1L;
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    public static final GamePurchaseResponse GAME_PURCHASE_RESPONSE =
            new GamePurchaseResponse(GAME_ID, "Game", BigDecimal.valueOf(29.99));

    public static Notification createNotificationEntity() {
        return Notification.builder()
                .id(ID)
                .userId(USER_ID)
                .message(MESSAGE)
                .tipo(TIPO)
                .gameId(GAME_ID)
                .build();
    }

    public static Notification createNotificationEntityFaker() {
        return Notification.builder()
                .id(FAKER.number().randomNumber())
                .userId(FAKER.number().randomNumber())
                .message(FAKER.lorem().sentence())
                .tipo(TipoNotification.values()[FAKER.number().numberBetween(0, TipoNotification.values().length)])
                .gameId(FAKER.number().randomNumber())
                .build();
    }

    public static final NotificationRequest NOTIFICATION_REQUEST =
            NotificationRequest.builder()
                    .userId(USER_ID)
                    .message(MESSAGE)
                    .tipo(TIPO)
                    .gameId(GAME_ID)
                    .build();

    public static NotificationRequest createNotificationRequestFaker() {
        return NotificationRequest.builder()
                .userId(FAKER.number().randomNumber())
                .message(FAKER.lorem().sentence())
                .tipo(TipoNotification.values()[FAKER.number().numberBetween(0, TipoNotification.values().length)])
                .gameId(FAKER.number().randomNumber())
                .build();
    }

    public static final NotificationResponse NOTIFICATION_RESPONSE =
            NotificationResponse.builder()
                    .id(ID)
                    .userId(USER_ID)
                    .message(MESSAGE)
                    .tipo(TIPO)
                    .gameId(GAME_ID)
                    .fechaCreacion(FECHA)
                    .build();

    public static final PurchaseNotificationRequest PURCHASE_NOTIFICATION_REQUEST =
            PurchaseNotificationRequest.builder()
                    .userId(USER_ID)
                    .message("Compra realizada")
                    .tipo(TipoNotification.COMPRA)
                    .juegos(List.of(GAME_PURCHASE_RESPONSE))
                    .build();

    public static GamePurchaseResponse createGamePurchaseResponseFaker() {
        return GamePurchaseResponse.builder()
                .id(FAKER.number().randomNumber())
                .name(FAKER.gameOfThrones().house())
                .precio(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)))
                .build();
    }
}
