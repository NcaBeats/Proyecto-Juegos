package com.example.mspurchase.controller;

import com.example.mspurchase.dto.PurchaseRequest;
import com.example.mspurchase.dto.PurchaseResponse;
import com.example.mspurchase.dto.external.JuegoResponse;
import com.example.mspurchase.dto.external.UserResponse;
import com.example.mspurchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> realizarCompra(@Valid @RequestBody PurchaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseService.registrarCompra(request));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<Page<PurchaseResponse>> verHistorial(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(purchaseService.obtenerHistorialPaginado(usuarioId, PageRequest.of(page, size)));
    }
}
