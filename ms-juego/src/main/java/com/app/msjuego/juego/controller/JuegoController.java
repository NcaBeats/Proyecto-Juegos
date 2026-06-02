package com.app.msjuego.juego.controller;

import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.service.JuegoService;
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
@RequestMapping("api/v1/juegos")
public class JuegoController {
    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<Page<JuegoResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/juegos - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(juegoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById (@Valid @PathVariable Long id){
        log.debug("GET /api/v1/juegos/{}", id);
        return ResponseEntity.ok(juegoService.findById(id));
    }

    @GetMapping("/estudio/{estudioId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByEstudioId(@PathVariable Long estudioId, Pageable pageable) {
        log.debug("GET /api/v1/juegos/estudio/{} - página: {} tamaño: {}", estudioId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(juegoService.getAllByEstudioId(estudioId, pageable));
    }

    @GetMapping("/genero/{generoId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByGenerosId(@PathVariable Long generoId, Pageable pageable) {
        log.debug("GET /api/v1/juegos/genero/{} - página: {} tamaño: {}", generoId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(juegoService.getAllByGenerosId(generoId, pageable));
    }

    @GetMapping("/plataforma/{plataformaId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByPlataformasId(@PathVariable Long plataformaId, Pageable pageable) {
        log.debug("GET /api/v1/juegos/plataforma/{} - página: {} tamaño: {}", plataformaId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(juegoService.getAllByPlataformasId(plataformaId, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<JuegoResponse> buscarPorNombre(@RequestParam String nombre) {
        log.debug("GET /api/v1/juegos/buscar nombre={}", nombre);
        return ResponseEntity.ok(juegoService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<JuegoResponse> save(@Valid @RequestBody JuegoRequest request) {
        log.info("POST /api/v1/juegos - creando juego");
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponse> update(@PathVariable Long id, @Valid @RequestBody JuegoRequest request) {
        log.info("PUT /api/v1/juegos/{} - actualizando juego", id);
        return ResponseEntity.ok(juegoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/juegos/{} - eliminando juego", id);
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
