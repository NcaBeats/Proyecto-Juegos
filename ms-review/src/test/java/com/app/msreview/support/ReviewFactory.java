package com.app.msreview.support;

import com.app.msreview.dto.ReviewRequest;
import com.app.msreview.dto.ReviewResponse;
import com.app.msreview.dto.external.JuegoResponse;
import com.app.msreview.dto.external.ProfileResponse;
import com.app.msreview.model.Rating;
import com.app.msreview.model.Review;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class ReviewFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final Long USER_ID = 1L;
    public static final String NICKNAME = "User";
    public static final String AVATAR = "https://example.com/avatar.jpg";
    public static final Long JUEGO_ID = 1L;
    public static final String GAME_NAME = "Game";
    public static final String COMENTARIO = "Buen juego, lo recomiendo.";
    public static final Rating RATING = Rating.CUATRO_ESTRELLAS;
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");
    public static final JuegoResponse JUEGO_RESPONSE = new JuegoResponse(JUEGO_ID, GAME_NAME);
    public static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse(USER_ID, NICKNAME, AVATAR);

    public static Review createReviewEntity(){
        return Review.builder()
                .id(ID)
                .userId(USER_ID)
                .juegoId(JUEGO_ID)
                .comentario(COMENTARIO)
                .rating(RATING)
                .build();
    }
    public static Review createReviewEntityFaker(){
        return Review.builder()
                .id(FAKER.number().randomNumber())
                .userId(FAKER.number().randomNumber())
                .juegoId(FAKER.number().randomNumber())
                .comentario(FAKER.lorem().sentence())
                .rating(Rating.values()[FAKER.number().numberBetween(1,5)])
                .build();
    }

    public static final ReviewRequest REVIEW_REQUEST =
            new ReviewRequest(USER_ID, JUEGO_ID, COMENTARIO, RATING);

    public static ReviewRequest createReviewRequestFaker(){
        return new ReviewRequest(
                FAKER.number().randomNumber(),
                FAKER.number().randomNumber(),
                FAKER.lorem().sentence(),
                Rating.values()[FAKER.number().numberBetween(1,5)]
        );
    }

    public static final ReviewResponse REVIEW_RESPONSE =
            new ReviewResponse(
                    ID,
                    USER_ID,
                    NICKNAME,
                    AVATAR,
                    JUEGO_ID,
                    GAME_NAME,
                    COMENTARIO,
                    RATING.getValue(),
                    FECHA);
}
