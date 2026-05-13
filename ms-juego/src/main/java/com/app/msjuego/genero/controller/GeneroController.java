package com.app.msjuego.genero.controller;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/generos")
public class GeneroController {
    private final GeneroService generoService;

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponse> findById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(generoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GeneroResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(generoService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<GeneroResponse> save(@Valid @RequestBody GeneroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponse> update(@PathVariable Long id, @Valid @RequestBody GeneroRequest request) {
        return ResponseEntity.ok(generoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        generoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}