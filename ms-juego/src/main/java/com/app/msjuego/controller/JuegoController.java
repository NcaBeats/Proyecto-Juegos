package com.app.msjuego.controller;

import com.app.msjuego.dto.JuegoResponse;
import com.app.msjuego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/juegos")
public class JuegoController {
    private final JuegoService juegoService;

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById (@Valid @PathVariable Long id){
        return ResponseEntity.ok(juegoService.findById(id));
    }
}
