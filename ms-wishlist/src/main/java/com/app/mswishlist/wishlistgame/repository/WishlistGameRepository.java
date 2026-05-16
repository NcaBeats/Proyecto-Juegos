package com.app.mswishlist.wishlistgame.repository;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface WishlistGameRepository extends JpaRepository<WishlistGame,Long> {
    Set<WishlistGame> getAllByWishlistUserId(Long userId);
    boolean existsByWishlistUserIdAndGameId(Long userId, Long gameId);
    Optional<WishlistGame> findByWishlistUserIdAndGameId(Long userId, Long gameId);
}
