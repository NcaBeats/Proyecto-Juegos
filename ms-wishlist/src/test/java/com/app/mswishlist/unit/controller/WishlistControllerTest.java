package com.app.mswishlist.unit.controller;

import com.app.mswishlist.wishlist.controller.WishlistController;
import com.app.mswishlist.wishlistgame.service.WishlistGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.app.mswishlist.support.WishlistFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    WishlistGameService wishlistGameService;

    @InjectMocks
    WishlistController wishlistController;

    @Test
    void getAllByUserId_ReturnsOk() {
        when(wishlistGameService.getAllByUserId(USER_ID)).thenReturn(createWishlistResponse());

        var result = wishlistController.getAllByUserId(USER_ID);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(createWishlistResponse(), result.getBody());
    }

    @Test
    void addGame_ReturnsCreated() {
        when(wishlistGameService.addGame(USER_ID, createWishlistGameRequest())).thenReturn(createWishlistGameResponse());

        var result = wishlistController.addGame(USER_ID, createWishlistGameRequest());

        assertEquals(201, result.getStatusCode().value());
        assertEquals(createWishlistGameResponse(), result.getBody());
    }

    @Test
    void deleteGame_ReturnsNoContent() {
        doNothing().when(wishlistGameService).deleteGame(USER_ID, GAME_ID);

        var result = wishlistController.deleteGame(USER_ID, GAME_ID);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }

}
