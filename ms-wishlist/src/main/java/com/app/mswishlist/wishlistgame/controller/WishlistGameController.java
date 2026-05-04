package com.app.mswishlist.wishlistgame.controller;

import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.service.WishlistGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist-games")
public class WishlistGameController {
    private final WishlistGameService wishlistGameService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<WishlistGameResponse>> getAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(wishlistGameService.getAllByUserId(userId,pageable));
    }
    @PostMapping
    public ResponseEntity<WishlistGameResponse> addGame(@RequestBody @Valid WishlistGameRequest wishlistGameRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistGameService.addGame(wishlistGameRequest));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteGame(@RequestBody @Valid WishlistGameRequest wishlistGameRequest) {
        wishlistGameService.deleteGame(wishlistGameRequest);
        return ResponseEntity.noContent().build();
    }
}
