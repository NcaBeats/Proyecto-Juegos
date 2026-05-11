package com.example.msreview.controller;

import com.example.msreview.dto.ReviewRequest;
import com.example.msreview.dto.ReviewResponse;
import com.example.msreview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<ReviewResponse>> findAllByJuegoId(@PathVariable Long gameId) {
        return ResponseEntity.ok(reviewService.findAllByJuegoId(gameId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.findAllByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<ReviewResponse> save(@Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.save(reviewRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
