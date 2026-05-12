package com.app.mswishlist.wishlist.service;

import com.app.mswishlist.wishlist.client.ProfileClient;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProfileClient profileClient;

    @Transactional
    public Wishlist getOrCreate (Long userId) {
        profileClient.getProfileByUserId(userId);

        return wishlistRepository.findById(userId)
                .orElseGet(()-> wishlistRepository.save(
                        Wishlist.builder()
                                .userId(userId)
                                .build()
                ));

    }

    @Transactional(readOnly = true)
    public Optional<Wishlist> findById(Long userId) {
        return wishlistRepository.findById(userId);
    }
}
