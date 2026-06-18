package com.app.mspurchase.support;

import com.app.mspurchase.purchasegame.dto.PurchaseGameRequest;
import com.app.mspurchase.purchasegame.dto.PurchaseGameResponse;
import com.app.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import com.app.mspurchase.purchasegame.dto.external.JuegoResponse;
import com.app.mspurchase.purchasegame.model.PurchaseGame;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;

public class PurchaseGameFactory {
    private static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long PURCHASE_GAME_ID = 1L;
    public static final Long GAME_ID = 1L;
    public static final Long GAME_ID2 = 2L;
    public static final String GAME_NAME = "Juego";
    public static final BigDecimal PRECIO = BigDecimal.valueOf(50);
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");
    public static final JuegoResponse JUEGO_RESPONSE = new JuegoResponse(GAME_ID, GAME_NAME, PRECIO);

    public static final PurchaseGameRequest PURCHASE_GAME_REQUEST = PurchaseGameRequest.builder()
            .gameId(GAME_ID)
            .build();

    public static final PurchaseGameResponse PURCHASE_GAME_RESPONSE = PurchaseGameResponse.builder()
            .id(PURCHASE_GAME_ID)
            .gameId(GAME_ID)
            .fechaRegistro(FECHA_REGISTRO)
            .build();

    public static final PurchaseGameStatsResponse PURCHASE_GAME_STATS_RESPONSE = PurchaseGameStatsResponse.builder()
            .userId(1L)
            .gameId(GAME_ID)
            .gameName(GAME_NAME)
            .cantidad(1)
            .price(PRECIO)
            .build();

    public static PurchaseGame createPurchaseGameEntity() {
        return PurchaseGame.builder()
                .id(PURCHASE_GAME_ID)
                .gameId(GAME_ID)
                .fechaRegistro(FECHA_REGISTRO)
                .build();
    }

    public static PurchaseGame createPurchaseGameEntityFaker() {
        return PurchaseGame.builder()
                .id(FAKER.number().randomNumber())
                .gameId(FAKER.number().randomNumber())
                .build();
    }

    public static PurchaseGameStatsResponse createPurchaseGameStatsResponseFaker() {
        return PurchaseGameStatsResponse.builder()
                .userId(FAKER.number().randomNumber())
                .gameId(FAKER.number().randomNumber())
                .gameName(FAKER.lorem().word())
                .cantidad(1)
                .price(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)))
                .build();
    }
}
