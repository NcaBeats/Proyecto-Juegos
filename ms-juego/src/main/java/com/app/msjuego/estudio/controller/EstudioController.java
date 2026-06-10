package com.app.msjuego.estudio.controller;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.service.EstudioService;
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
@RequestMapping("api/v1/estudios")
public class EstudioController {
    private final EstudioService estudioService;

    @GetMapping("/{id}")
    public ResponseEntity<EstudioResponse> findById(@Valid @PathVariable Long id) {
        log.debug("GET /api/v1/estudios/{}", id);
        return ResponseEntity.ok(estudioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EstudioResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/estudios - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(estudioService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<EstudioResponse> save(@Valid @RequestBody EstudioRequest request) {
        log.info("POST /api/v1/estudios - creando estudio");
        return ResponseEntity.status(HttpStatus.CREATED).body(estudioService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudioResponse> update(@PathVariable Long id, @Valid @RequestBody EstudioRequest request) {
        log.info("PUT /api/v1/estudios/{} - actualizando estudio", id);
        return ResponseEntity.ok(estudioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/estudios/{} - eliminando estudio", id);
        estudioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
