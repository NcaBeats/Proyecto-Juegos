package com.app.mswishlist.unit.wishlist.service;

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
    void getOrCreate_CreatesNew() {
        Wishlist wishlist = createWishlistEntityFaker();
        when(profileClient.getProfileByUserId(wishlist.getUserId())).thenReturn(PROFILE_RESPONSE);
        when(wishlistRepository.findById(wishlist.getUserId())).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        var result = wishlistService.getOrCreate(wishlist.getUserId());

        assertNotNull(result);
        verify(profileClient).getProfileByUserId(wishlist.getUserId());
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void getOrCreate_ReturnsExisting() {
        Wishlist wishlist = createWishlistEntityFaker();
        when(profileClient.getProfileByUserId(wishlist.getUserId())).thenReturn(PROFILE_RESPONSE);
        when(wishlistRepository.findById(wishlist.getUserId())).thenReturn(Optional.of(wishlist));

        var result = wishlistService.getOrCreate(wishlist.getUserId());

        assertNotNull(result);
        verify(profileClient).getProfileByUserId(wishlist.getUserId());
        verify(wishlistRepository).findById(wishlist.getUserId());
    }
}
