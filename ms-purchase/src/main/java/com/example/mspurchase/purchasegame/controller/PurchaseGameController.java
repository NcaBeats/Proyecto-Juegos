package com.example.mspurchase.purchasegame.controller;

import com.example.mspurchase.purchasegame.dto.PurchaseFullResponse;
import com.example.mspurchase.purchasegame.service.PurchaseGameService;
import com.example.mspurchase.purchasegame.repository.PurchaseGameRepository; // Importación necesaria
import com.example.mspurchase.purchasegame.model.PurchaseGame; // Importación necesaria
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-games")
@RequiredArgsConstructor
public class PurchaseGameController {
    private final PurchaseGameService purchaseGameService;
    private final PurchaseGameRepository pgRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<PurchaseFullResponse> create(
            @PathVariable Long userId,
            @RequestBody List<Long> gameIds) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseGameService.processPurchase(userId, gameIds));
    }

    @GetMapping("/details/{purchaseId}")
    public ResponseEntity<List<PurchaseGame>> getDetails(@PathVariable Long purchaseId) {
        return ResponseEntity.ok(pgRepository.findAllByPurchaseId(purchaseId));
    }
}

