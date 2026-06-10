package com.app.msjuego.plataforma.controller;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.service.PlataformaService;
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
@RequestMapping("api/v1/plataformas")
public class PlataformaController {
    private final PlataformaService plataformaService;

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponse> findById(@Valid @PathVariable Long id) {
        log.debug("GET /api/v1/plataformas/{}", id);
        return ResponseEntity.ok(plataformaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PlataformaResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/plataformas - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(plataformaService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PlataformaResponse> save(@Valid @RequestBody PlataformaRequest request) {
        log.info("POST /api/v1/plataformas - creando plataforma");
        return ResponseEntity.status(HttpStatus.CREATED).body(plataformaService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponse> update(@PathVariable Long id, @Valid @RequestBody PlataformaRequest request) {
        log.info("PUT /api/v1/plataformas/{} - actualizando plataforma", id);
        return ResponseEntity.ok(plataformaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/plataformas/{} - eliminando plataforma", id);
        plataformaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
