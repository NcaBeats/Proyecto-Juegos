package com.example.mspurchase.purchase.controller;

import com.example.mspurchase.purchase.dto.PurchaseRequest;
import com.example.mspurchase.purchase.dto.PurchaseResponse;
import com.example.mspurchase.purchase.service.PurchaseService;
import com.example.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<PurchaseResponse>> findAllByUserId(@PathVariable Long id, Pageable pageable) {
        log.debug("GET /api/v1/purchases/{} - página: {} tamaño: {}", id, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(purchaseService.findAllByUserId(id, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/purchases - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(purchaseService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest request) {
        log.info("POST /api/v1/purchases - creando compra userId={} juegosCount={}", request.userId(), request.juegos().size());
        PurchaseResponse response = purchaseService.createPurchase(request);
        log.info("Compra creada para userId={}" , request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/game/{gameId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getGameStats(@PathVariable Long gameId) {
        log.debug("GET /api/v1/purchases/game/{}/stats - obteniendo stats de juego", gameId);
        return ResponseEntity.ok(purchaseService.findAllByGameIdForStats(gameId));
    }

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getUserStats(@PathVariable Long userId) {
        log.debug("GET /api/v1/purchases/user/{}/stats - obteniendo stats de usuario", userId);
        return ResponseEntity.ok(purchaseService.findAllByUserIdForStats(userId));
    }
}
