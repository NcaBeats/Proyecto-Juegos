package com.app.mswishlist.support;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import com.app.mswishlist.wishlistgame.model.WishlistGame;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public class WishlistFactory {

    public static final Long USER_ID = 1L;
    public static final Long GAME_ID = 2L;
    public static final String NICKNAME = "tester";


    public static Wishlist createWishlist() {
        return Wishlist.builder().userId(USER_ID).build();
    }

    public static WishlistGame createWishlistGameEntity() {
        return WishlistGame.builder().gameId(GAME_ID).build();
    }

    public static WishlistGameRequest createWishlistGameRequest() {
        return WishlistGameRequest.builder().gameId(GAME_ID).build();
    }

    public static JuegoResponse createJuegoResponse() {
        return JuegoResponse.builder()
                .id(GAME_ID)
                .nombre("Game Test")
                .precio(BigDecimal.valueOf(49.99))
                .fechaLanzamiento(LocalDate.of(2024, 1, 1))
                .build();
    }

    public static WishlistGameResponse createWishlistGameResponse() {
        return WishlistGameResponse.builder()
                .id(1L)
                .gameId(GAME_ID)
                .gameName("Game Test")
                .price(BigDecimal.valueOf(49.99))
                .fechaLanzamiento(LocalDate.of(2024, 1, 1))
                .fechaCreacion(Instant.parse("2024-01-01T00:00:00Z"))
                .build();
    }

    public static WishListResponse createWishlistResponse() {
        return WishListResponse.builder()
                .userId(USER_ID)
                .nickname(NICKNAME)
                .games(Set.of(createWishlistGameResponse()))
                .build();
    }

    public static ProfileResponse createProfileResponse() {
        return createProfileResponse(NICKNAME);
    }

    public static ProfileResponse createProfileResponse(String nickname) {
        return ProfileResponse.builder().nickname(nickname).build();
    }
}


