package com.example.msreview.controller;

import com.example.msreview.dto.ReviewRequest;
import com.example.msreview.dto.ReviewResponse;
import com.example.msreview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<ReviewResponse>> findAllByJuegoId(@PathVariable Long gameId) {
        log.debug("GET /api/v1/reviews/game/{} - obteniendo reviews", gameId);
        return ResponseEntity.ok(reviewService.findAllByJuegoId(gameId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> findAllByUserId(@PathVariable Long userId) {
        log.debug("GET /api/v1/reviews/user/{} - obteniendo reviews", userId);
        return ResponseEntity.ok(reviewService.findAllByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<ReviewResponse> save(@Valid @RequestBody ReviewRequest reviewRequest) {
        log.info("POST /api/v1/reviews - creando review userId={} juegoId={}", reviewRequest.userId(), reviewRequest.juegoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.save(reviewRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        log.info("PUT /api/v1/reviews/{} - actualizando review userId={}", id, reviewRequest.userId());
        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/reviews/{} - eliminando review", id);
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
