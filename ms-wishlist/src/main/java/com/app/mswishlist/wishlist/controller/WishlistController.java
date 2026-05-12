package com.app.mswishlist.wishlist.controller;

import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.service.WishlistGameService;
import com.app.mswishlist.wishlist.dto.WishListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlists")
public class WishlistController {
    private final WishlistGameService wishlistGameService;

    @GetMapping("/{userId}")
    public ResponseEntity<WishListResponse> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistGameService.getAllByUserId(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<WishlistGameResponse> addGame(@PathVariable Long userId, @RequestBody @Valid WishlistGameRequest wishlistGameRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistGameService.addGame(userId, wishlistGameRequest));
    }

    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long userId, @PathVariable Long gameId) {
        wishlistGameService.deleteGame(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}