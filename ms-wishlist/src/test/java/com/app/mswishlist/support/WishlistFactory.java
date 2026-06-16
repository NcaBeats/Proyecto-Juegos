package com.app.mswishlist.support;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class WishlistFactory {

    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long USER_ID = 1L;
    public static final Long GAME_ID = 2L;
    public static final String NICKNAME = "tester";
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");


    public static Wishlist createWishlistEntity() {
        return Wishlist.builder()
                .userId(USER_ID)
                .fechaCreacion(FECHA)
                .games(new HashSet<>())
                .build();
    }

//    public static WishlistGame createWishlistGameEntity() {
//        return WishlistGame.builder().gameId(GAME_ID).build();
//    }

//    public static WishlistGameRequest createWishlistGameRequest() {
//        return WishlistGameRequest.builder().gameId(GAME_ID).build();
//    }

//    public static JuegoResponse createJuegoResponse() {
//        return JuegoResponse.builder()
//                .id(GAME_ID)
//                .nombre("Game Test")
//                .precio(BigDecimal.valueOf(49.99))
//                .fechaLanzamiento(LocalDate.of(2024, 1, 1))
//                .build();
//    }

//    public static WishlistGameResponse createWishlistGameResponse() {
//        return WishlistGameResponse.builder()
//                .id(1L)
//                .gameId(GAME_ID)
//                .gameName("Game Test")
//                .price(BigDecimal.valueOf(49.99))
//                .fechaLanzamiento(LocalDate.of(2024, 1, 1))
//                .fechaCreacion(Instant.parse("2024-01-01T00:00:00Z"))
//                .build();
//    }

    public static WishListResponse createWishlistResponse() {
        return WishListResponse.builder()
                .userId(USER_ID)
                .nickname(NICKNAME)
                .games(Set.of(createWishlistGameResponse()))
                .build();
    }
}


