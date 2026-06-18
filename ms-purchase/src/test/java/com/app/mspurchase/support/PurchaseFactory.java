package com.app.mspurchase.support;

import com.app.mspurchase.purchase.dto.PurchaseRequest;
import com.app.mspurchase.purchase.dto.PurchaseResponse;
import com.app.mspurchase.purchase.dto.external.UserResponse;
import com.app.mspurchase.purchase.model.Purchase;
import com.app.mspurchase.purchasegame.dto.PurchaseGameRequest;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import static com.app.mspurchase.support.PurchaseGameFactory.*;

public class PurchaseFactory {
    private static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final Long USER_ID = 1L;
    public static final BigDecimal TOTAL_PRECIO = BigDecimal.valueOf(100);
    public static final BigDecimal SALDO_SUFICIENTE = BigDecimal.valueOf(100);
    public static final BigDecimal SALDO_INSUFICIENTE = BigDecimal.valueOf(30);
    public static final Instant FECHA_COMPRA = Instant.parse("2024-01-01T00:00:00Z");
    public static final UserResponse USER_RESPONSE_SUFICIENTE = UserResponse.builder()
            .id(USER_ID)
            .saldo(SALDO_SUFICIENTE)
            .build();
    public static final UserResponse USER_RESPONSE_INSUFICIENTE = UserResponse.builder()
            .id(USER_ID)
            .saldo(SALDO_INSUFICIENTE)
            .build();

    public static final PurchaseRequest PURCHASE_REQUEST = PurchaseRequest.builder()
            .userId(USER_ID)
            .juegos(List.of(PURCHASE_GAME_REQUEST))
            .build();

    public static final PurchaseResponse PURCHASE_RESPONSE = PurchaseResponse.builder()
            .id(ID)
            .userId(USER_ID)
            .totalPrecio(TOTAL_PRECIO)
            .fechaCompra(FECHA_COMPRA)
            .juegos(List.of(PURCHASE_GAME_RESPONSE))
            .build();

    public static Purchase createPurchaseEntity() {
        return Purchase.builder()
                .id(ID)
                .userId(USER_ID)
                .totalPrecio(TOTAL_PRECIO)
                .fechaCompra(FECHA_COMPRA)
                .build();
    }

    public static Purchase createPurchaseEntityFaker() {
        return Purchase.builder()
                .id(FAKER.number().randomNumber())
                .userId(FAKER.number().randomNumber())
                .totalPrecio(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)))
                .build();
    }

    public static PurchaseRequest createPurchaseRequestFaker() {
        return PurchaseRequest.builder()
                .userId(FAKER.number().randomNumber())
                .juegos(List.of(
                        PurchaseGameRequest.builder()
                                .gameId(FAKER.number().randomNumber())
                                .build()
                ))
                .build();
    }
}
