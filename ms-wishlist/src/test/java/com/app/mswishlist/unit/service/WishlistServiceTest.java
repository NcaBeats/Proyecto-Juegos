package com.app.mswishlist.unit.service;

import com.app.mswishlist.wishlist.client.ProfileClient;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlist.repository.WishlistRepository;
import com.app.mswishlist.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.app.mswishlist.support.WishlistFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    WishlistRepository wishlistRepository;
    @Mock
    ProfileClient profileClient;

    @InjectMocks
    WishlistService wishlistService;

    @Test
    void getOrCreate_ReturnsExisting() {
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(createProfileResponse());
        when(wishlistRepository.findById(USER_ID)).thenReturn(Optional.of(WISHLIST));

        var result = wishlistService.getOrCreate(USER_ID);

        assertNotNull(result);
        verify(profileClient).getProfileByUserId(USER_ID);
        verify(wishlistRepository).findById(USER_ID);
    }

    @Test
    void getOrCreate_CreatesNew() {
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(createProfileResponse());
        when(wishlistRepository.findById(USER_ID)).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(WISHLIST);

        var result = wishlistService.getOrCreate(USER_ID);

        assertNotNull(result);
        verify(profileClient).getProfileByUserId(USER_ID);
        verify(wishlistRepository).save(any(Wishlist.class));
    }
}
