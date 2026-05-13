package com.example.mspurchase.purchase.controller;

import com.example.mspurchase.purchase.dto.PurchaseRequest;
import com.example.mspurchase.purchase.dto.PurchaseResponse;
import com.example.mspurchase.purchase.service.PurchaseService;
import com.example.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<PurchaseResponse>> findAllByUserId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(purchaseService.findAllByUserId(id, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(purchaseService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest request) {
        PurchaseResponse response = purchaseService.createPurchase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/game/{gameId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getGameStats(@PathVariable Long gameId) {
        return ResponseEntity.ok(purchaseService.findAllByGameIdForStats(gameId));
    }

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getUserStats(@PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.findAllByUserIdForStats(userId));
    }
}