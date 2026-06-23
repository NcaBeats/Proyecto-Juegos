package com.app.mswishlist.wishlist.service;

import com.app.mswishlist.wishlist.client.ProfileClient;
import com.app.mswishlist.wishlist.model.Wishlist;
import com.app.mswishlist.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProfileClient profileClient;

    @Transactional
    public Wishlist getOrCreate (Long userId) {
        log.debug("Obteniendo o creando wishlist userId={}", userId);
        log.debug("Llamando a ProfileClient.getProfileByUserId userId={}", userId);
        profileClient.getProfileByUserId(userId);

        var existing = wishlistRepository.findById(userId);
        if (existing.isPresent()) {
            log.debug("Wishlist existente userId={}", userId);
            return existing.get();
        }

        Wishlist created = wishlistRepository.save(
                Wishlist.builder()
                        .userId(userId)
                        .build()
        );
        log.info("Wishlist creada userId={}", userId);
        return created;

    }
}
