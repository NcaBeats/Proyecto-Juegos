package com.app.mswishlist.support;

import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

import static com.app.mswishlist.support.WishlistFactory.createWishlistEntity;

public class WishlistGameFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final Long GAME_ID = 1L;
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
    public static final WishlistGameRequest WISHLIST_GAME =
            new WishlistGameRequest(
                    GAME_ID
            );
    public static WishlistGameRequest createWishlistGameRequestFaker(){
        return new WishlistGameRequest(
                FAKER.number().randomNumber()
        );
    }
}
