package com.app.msjuego.plataforma.controller;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.service.PlataformaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/plataformas")
public class PlataformaController {
    private final PlataformaService plataformaService;

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponse> findById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(plataformaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PlataformaResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(plataformaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PlataformaResponse> save(@Valid @RequestBody PlataformaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plataformaService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponse> update(@PathVariable Long id, @Valid @RequestBody PlataformaRequest request) {
        return ResponseEntity.ok(plataformaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        plataformaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}