package com.example.mspurchase.controller;

import com.example.mspurchase.dto.PurchaseRequest;
import com.example.mspurchase.dto.PurchaseResponse;
import com.example.mspurchase.dto.external.JuegoResponse;
import com.example.mspurchase.dto.external.UserResponse;
import com.example.mspurchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> realizarCompra(@Valid @RequestBody PurchaseRequest request) {
        JuegoResponse datosJuego = new JuegoResponse(request.juegoId(), "Validado", request.precio());
        UserResponse datosUsuario = new UserResponse(request.usuarioId(), "Validado", "N/A");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseService.registrarCompra(request, datosJuego, datosUsuario));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<List<PurchaseResponse>> verHistorial(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(purchaseService.obtenerHistorial(usuarioId));
    }
}
