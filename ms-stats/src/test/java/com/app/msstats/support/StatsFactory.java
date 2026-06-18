package com.app.msstats.support;

import com.app.msstats.dto.GameStatsResponse;
import com.app.msstats.dto.UserStatsResponse;
import com.app.msstats.dto.external.JuegoResponse;
import com.app.msstats.dto.external.ProfileResponse;
import com.app.msstats.dto.external.PurchaseGameResponse;
import com.app.msstats.dto.external.ReviewResponse;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Locale;

public class StatsFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long GAME_ID = 1L;
    public static final String GAME_NAME = "Game";
    public static final BigDecimal PRECIO = BigDecimal.valueOf(29.99);
    public static final Long USER_ID = 1L;
    public static final String NICKNAME = "User";
    public static final String AVATAR = "https://example.com/avatar.jpg";

    public static final JuegoResponse JUEGO_RESPONSE = new JuegoResponse(GAME_ID, GAME_NAME, PRECIO);
    public static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse(USER_ID, NICKNAME, AVATAR);

    public static final GameStatsResponse GAME_STATS_RESPONSE = GameStatsResponse.builder()
            .gameId(GAME_ID)
            .gameName(GAME_NAME)
            .copiasVendidas(5)
            .ventasTotales(BigDecimal.valueOf(149.95))
            .ratingPromedio(4.5)
            .build();

    public static final UserStatsResponse USER_STATS_RESPONSE = UserStatsResponse.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .juegosComprados(2)
            .dineroGastado(BigDecimal.valueOf(59.98))
            .promedioRatingDado(3.5)
            .build();

    public static PurchaseGameResponse createPurchaseGameResponse() {
        return PurchaseGameResponse.builder()
                .userId(USER_ID)
                .gameId(GAME_ID)
                .gameName(GAME_NAME)
                .cantidad(1)
                .price(PRECIO)
                .build();
    }

    public static PurchaseGameResponse createPurchaseGameResponseFaker() {
        return PurchaseGameResponse.builder()
                .userId(FAKER.number().randomNumber())
                .gameId(FAKER.number().randomNumber())
                .gameName(FAKER.gameOfThrones().house())
                .cantidad(FAKER.number().numberBetween(1, 5))
                .price(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)))
                .build();
    }

    public static ReviewResponse createReviewResponse() {
        return ReviewResponse.builder()
                .id(1L)
                .userId(USER_ID)
                .rating(4)
                .build();
    }

    public static ReviewResponse createReviewResponseFaker() {
        return ReviewResponse.builder()
                .id(FAKER.number().randomNumber())
                .userId(FAKER.number().randomNumber())
                .rating(FAKER.number().numberBetween(1, 6))
                .build();
    }
}
