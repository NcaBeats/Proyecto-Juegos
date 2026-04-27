package com.app.msjuego.controller;

import com.app.msjuego.dto.JuegoRequest;
import com.app.msjuego.dto.JuegoResponse;
import com.app.msjuego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/juegos")
public class JuegoController {
    private final JuegoService juegoService;

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById (@Valid @PathVariable Long id){
        return ResponseEntity.ok(juegoService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<JuegoResponse> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(juegoService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<JuegoResponse> save(@Valid @RequestBody JuegoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponse> update(@PathVariable Long id, @Valid @RequestBody JuegoRequest request) {
        return ResponseEntity.ok(juegoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
