package com.app.mswishlist.support;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

import static com.app.mswishlist.support.WishlistFactory.createWishlistEntity;

public class WishlistGameFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final Long GAME_ID = 1L;
    public static final String GAME_NAME = "Game Name";
    public static final BigDecimal PRICE = BigDecimal.valueOf(59.99);
    public static final LocalDate FECHA_LANZAMIENTO = LocalDate.of(2024,1,1);
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    public static WishlistGame createWishlistGameEntity(){
        Wishlist wishlist = createWishlistEntity();
        return WishlistGame.builder()
                .id(ID)
                .gameId(GAME_ID)
                .wishlist(wishlist)
                .fechaCreacion(FECHA)
                .build();
    }
    public static WishlistGame createWishlistGameEntityFaker(){
        Wishlist wishlist = createWishlistEntity();
        return new WishlistGame(
                FAKER.number().randomNumber(),
                FAKER.number().randomNumber(),
                wishlist,
                FECHA
        );
    }
    public static final WishlistGameRequest WISHLIST_GAME_REQUEST =
            new WishlistGameRequest(
                    GAME_ID
            );
    public static WishlistGameRequest createWishlistGameRequestFaker(){
        return new WishlistGameRequest(
                FAKER.number().randomNumber()
        );
    }
    public static final WishlistGameResponse WISHLIST_GAME_RESPONSE =
            new WishlistGameResponse(
                    ID,
                    GAME_ID,
                    GAME_NAME,
                    PRICE,
                    FECHA_LANZAMIENTO,
                    FECHA
            );
    public static final ProfileResponse PROFILE_RESPONSE =
            new ProfileResponse(
                    FAKER.number().randomNumber(),
                    FAKER.name().firstName()
            );
    public static final JuegoResponse JUEGO_RESPONSE =
            new JuegoResponse(
                    ID,
                    GAME_NAME,
                    PRICE,
                    FECHA_LANZAMIENTO
            );
}
