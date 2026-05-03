package com.app.mswishlist.repository;

import com.app.mswishlist.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}