package com.app.mswishlist.wishlistgame.repository;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistGameRepository extends JpaRepository<WishlistGame,Long> {
    Page<WishlistGame> getAllByUserId(Long userId, Pageable pageable);
    void deleteByUserIdAndGameId(Long userId, Long gameId);
    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}
