package com.app.msjuego.genero.controller;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/generos")
public class GeneroController {
    private final GeneroService generoService;

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponse> findById(@Valid @PathVariable Long id) {
        log.debug("GET /api/v1/generos/{}", id);
        return ResponseEntity.ok(generoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GeneroResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/generos - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(generoService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<GeneroResponse> save(@Valid @RequestBody GeneroRequest request) {
        log.info("POST /api/v1/generos - creando genero");
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponse> update(@PathVariable Long id, @Valid @RequestBody GeneroRequest request) {
        log.info("PUT /api/v1/generos/{} - actualizando genero", id);
        return ResponseEntity.ok(generoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/generos/{} - eliminando genero", id);
        generoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
