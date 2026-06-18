package com.app.mswishlist.unit.wishlistgame.service;

import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlistgame.client.JuegoClient;
import com.app.mswishlist.wishlistgame.client.NotificationClient;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.external.NotificationRequest;
import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
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

import static com.app.mswishlist.support.WishlistFactory.createWishlistEntity;
import static com.app.mswishlist.support.WishlistFactory.createWishlistEntityFaker;
import static com.app.mswishlist.support.WishlistGameFactory.*;
import static com.app.mswishlist.support.WishlistGameFactory.createWishlistGameEntityFaker;
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
        WishlistGame wg = createWishlistGameEntityFaker();
        when(profileClient.getProfileByUserId(wg.getId())).thenReturn(PROFILE_RESPONSE);
        when(wishlistGameRepository.getAllByWishlistUserId(wg.getId())).thenReturn(Set.of(wg));
        when(juegoClient.getJuegoById(wg.getGameId())).thenReturn(JUEGO_RESPONSE);
        when(wishlistGameMapper.toResponse(wg,JUEGO_RESPONSE)).thenReturn(WISHLIST_GAME_RESPONSE);

        WishListResponse result = wishlistGameService.getAllByUserId(wg.getId());

        assertNotNull(result);
        assertEquals(wg.getId(), result.userId());
    }

    @Test
    void addGame_Success_ReturnsResponse() {
        Wishlist wishlist = createWishlistEntity();
        WishlistGame wg = createWishlistGameEntity();
        when(wishlistService.getOrCreate(wishlist.getUserId())).thenReturn(wishlist);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(wishlist.getUserId(),WISHLIST_GAME_REQUEST.gameId())).thenReturn(false);
        when(juegoClient.getJuegoById(WISHLIST_GAME_REQUEST.gameId())).thenReturn(JUEGO_RESPONSE);
        when(wishlistGameMapper.toEntity(WISHLIST_GAME_REQUEST,wishlist)).thenReturn(wg);
        when(wishlistGameRepository.save(wg)).thenReturn(wg);
        doNothing().when(notificationClient).createNotification(any(NotificationRequest.class));
        when(wishlistGameMapper.toResponse(wg, JUEGO_RESPONSE)).thenReturn(WISHLIST_GAME_RESPONSE);

        var result = wishlistGameService.addGame(wishlist.getUserId(),WISHLIST_GAME_REQUEST);

        assertNotNull(result);
        assertEquals(GAME_ID, result.gameId());
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void addGame_AlreadyExists_Throw() {
        Wishlist wishlist = createWishlistEntityFaker();
        WishlistGameRequest wgRequest = createWishlistGameRequestFaker();
        when(wishlistService.getOrCreate(wishlist.getUserId())).thenReturn(wishlist);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(wishlist.getUserId(),wgRequest.gameId())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> wishlistGameService.addGame(wishlist.getUserId(),wgRequest));
    }

    @Test
    void addGame_NotificationThrows_DoesNotFail() {
        Wishlist wishlist = createWishlistEntity();
        WishlistGame wg = createWishlistGameEntity();
        when(wishlistService.getOrCreate(wishlist.getUserId())).thenReturn(wishlist);
        when(wishlistGameRepository.existsByWishlistUserIdAndGameId(wishlist.getUserId(),WISHLIST_GAME_REQUEST.gameId())).thenReturn(false);
        when(juegoClient.getJuegoById(WISHLIST_GAME_REQUEST.gameId())).thenReturn(JUEGO_RESPONSE);
        when(wishlistGameMapper.toEntity(WISHLIST_GAME_REQUEST,wishlist)).thenReturn(wg);
        when(wishlistGameRepository.save(wg)).thenReturn(wg);
        doThrow(new RuntimeException("notification failed")).when(notificationClient).createNotification(any(NotificationRequest.class));
        when(wishlistGameMapper.toResponse(wg, JUEGO_RESPONSE)).thenReturn(WISHLIST_GAME_RESPONSE);

        var result = wishlistGameService.addGame(wishlist.getUserId(), WISHLIST_GAME_REQUEST);

        assertNotNull(result);
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void deleteGame_Success() {
        Wishlist wishlist = createWishlistEntityFaker();
        WishlistGame wg = createWishlistGameEntityFaker();
        when(wishlistGameRepository.findByWishlistUserIdAndGameId(wishlist.getUserId(),wg.getGameId())).thenReturn(Optional.of(wg));

        wishlistGameService.deleteGame(wishlist.getUserId(),wg.getGameId());

        verify(wishlistGameRepository).delete(wg);
    }

    @Test
    void deleteGame_NotFound_Throw() {
        Wishlist wishlist = createWishlistEntityFaker();
        WishlistGame wg = createWishlistGameEntityFaker();
        when(wishlistGameRepository.findByWishlistUserIdAndGameId(wishlist.getUserId(),wg.getGameId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> wishlistGameService.deleteGame(wishlist.getUserId(),wg.getGameId()));
    }

}
