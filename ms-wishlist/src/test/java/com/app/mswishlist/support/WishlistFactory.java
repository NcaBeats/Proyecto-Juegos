package com.app.mswishlist.support;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;

public class WishlistFactory {

    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final String NICKNAME = "tester";
    public static final Long USER_ID = 1L;

    public static Wishlist createWishlistEntity() {
        return Wishlist.builder()
                .userId(USER_ID)
                .fechaCreacion(Instant.now())
                .games(new HashSet<>())
                .build();
    }
    public static Wishlist createWishlistEntityFaker(){
        return Wishlist.builder()
                .userId(FAKER.number().randomNumber())
                .fechaCreacion(Instant.now())
                .games(new HashSet<>())
                .build();
    }
    public static final WishListResponse WISHLIST_RESPONSE =
        new WishListResponse(
                USER_ID,
                NICKNAME,
                new HashSet<>()
                );
    public static final ProfileResponse PROFILE_RESPONSE =
            new ProfileResponse(
                    USER_ID,
                    NICKNAME
            );
}


