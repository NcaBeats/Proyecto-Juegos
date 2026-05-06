package com.app.mswishlist.wishlistgame.repository;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistGameRepository extends JpaRepository<WishlistGame,Long> {
    List<WishlistGame> getAllByWishlistUserId(Long userId);
    void deleteByWishlistUserIdAndGameId(Long userId, Long gameId);
    boolean existsByWishlistUserIdAndGameId(Long userId, Long gameId);
}
