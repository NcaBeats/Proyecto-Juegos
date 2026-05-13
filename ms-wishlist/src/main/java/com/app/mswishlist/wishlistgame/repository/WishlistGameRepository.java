package com.app.mswishlist.wishlistgame.repository;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistGameRepository extends JpaRepository<WishlistGame,Long> {
    List<WishlistGame> getAllByWishlistUserId(Long userId);
    void deleteByWishlistUserIdAndGameId(Long userId, Long gameId);
    boolean existsByWishlistUserIdAndGameId(Long userId, Long gameId);
    Optional<WishlistGame> findByWishlistUserIdAndGameId(Long userId, Long gameId);
}
