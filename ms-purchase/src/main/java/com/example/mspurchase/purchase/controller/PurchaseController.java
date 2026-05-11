package com.example.mspurchase.purchase.controller;

import com.example.mspurchase.purchase.dto.PurchaseRequest;
import com.example.mspurchase.purchase.dto.PurchaseResponse;
import com.example.mspurchase.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<Page<PurchaseResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(purchaseService.findAll(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<PurchaseResponse>> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(purchaseService.findAllByUserId(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest request) {
        PurchaseResponse response = purchaseService.createPurchase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}