package com.app.mswishlist.unit.service;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlistgame.client.JuegoClient;
import com.app.mswishlist.wishlistgame.client.NotificationClient;
import com.app.mswishlist.wishlistgame.dto.external.NotificationRequest;
import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;
import com.app.mswishlist.wishlistgame.repository.WishlistGameRepository;
import com.app.mswishlist.wishlistgame.service.WishlistGameService;
import com.app.mswishlist.wishlist.service.WishlistService;
import com.app.mswishlist.wishlist.client.ProfileClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.app.mswishlist.support.WishlistFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistGameServiceTest {

    @Mock
    WishlistService wishlistService;
    @Mock
    WishlistGameRepository wishlistGameRepository;
    @Mock
    WishlistGameMapper wishlistGameMapper;
    @Mock
    JuegoClient juegoClient;
    @Mock
    ProfileClient profileClient;
    @Mock
    NotificationClient notificationClient;

    @InjectMocks
    WishlistGameService wishlistGameService;

    @Test
    void getAllByUserId_ReturnsWishList() {
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(createProfileResponse());
        when(wishlistGameRepository.getAllByWishlistUserId(USER_ID)).thenReturn(Set.of(createWishlistGameEntity()));
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(createJuegoResponse());
        when(wishlistGameMapper.toResponse(createWishlistGameEntity(), createJuegoResponse())).thenReturn(createWishlistGameResponse());

        WishListResponse result = wishlistGameService.getAllByUserId(USER_ID);

        assertNotNull(result);
        assertEquals(USER_ID, result.userId());
    }

    @Test
    void addGame_Success_ReturnsResponse() {
        when(wishlistService.getOrCreate(USER_ID)).thenReturn(WISHLIST);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(false);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(wishlistGameMapper.toEntity(WISHLIST_GAME_REQUEST, WISHLIST)).thenReturn(WISHLIST_GAME_ENTITY);
        when(wishlistGameMapper.toResponse(WISHLIST_GAME_ENTITY, JUEGO_RESPONSE)).thenReturn(WISHLIST_GAME_RESPONSE);

        var result = wishlistGameService.addGame(USER_ID, WISHLIST_GAME_REQUEST);

        assertNotNull(result);
        assertEquals(GAME_ID, result.gameId());
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void addGame_AlreadyExists_Throw() {
        when(wishlistService.getOrCreate(USER_ID)).thenReturn(WISHLIST);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> wishlistGameService.addGame(USER_ID, WISHLIST_GAME_REQUEST));
    }

    @Test
    void addGame_NotificationThrows_DoesNotFail() {
        when(wishlistService.getOrCreate(USER_ID)).thenReturn(WISHLIST);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(false);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(wishlistGameMapper.toEntity(WISHLIST_GAME_REQUEST, WISHLIST)).thenReturn(WISHLIST_GAME_ENTITY);
        when(wishlistGameMapper.toResponse(WISHLIST_GAME_ENTITY, JUEGO_RESPONSE)).thenReturn(WISHLIST_GAME_RESPONSE);
        doThrow(new RuntimeException("notification failed")).when(notificationClient).createNotification(any());

        var result = wishlistGameService.addGame(USER_ID, WISHLIST_GAME_REQUEST);

        assertNotNull(result);
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void deleteGame_Success() {
        when(wishlistGameRepository.findByWishlistUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(Optional.of(WISHLIST_GAME_ENTITY));

        wishlistGameService.deleteGame(USER_ID, GAME_ID);

        verify(wishlistGameRepository).delete(WISHLIST_GAME_ENTITY);
    }

    @Test
    void deleteGame_NotFound_Throw() {
        when(wishlistGameRepository.findByWishlistUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> wishlistGameService.deleteGame(USER_ID, GAME_ID));
    }

}
