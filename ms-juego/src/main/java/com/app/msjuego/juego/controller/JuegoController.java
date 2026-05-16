package com.app.msjuego.juego.controller;

import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/juegos")
public class JuegoController {
    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<Page<JuegoResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(juegoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponse> findById (@Valid @PathVariable Long id){
        return ResponseEntity.ok(juegoService.findById(id));
    }

    @GetMapping("/estudio/{estudioId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByEstudioId(@PathVariable Long estudioId, Pageable pageable) {
        return ResponseEntity.ok(juegoService.getAllByEstudioId(estudioId, pageable));
    }

    @GetMapping("/genero/{generoId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByGenerosId(@PathVariable Long generoId, Pageable pageable) {
        return ResponseEntity.ok(juegoService.getAllByGenerosId(generoId, pageable));
    }

    @GetMapping("/plataforma/{plataformaId}")
    public ResponseEntity<Page<JuegoResponse>> getAllByPlataformasId(@PathVariable Long plataformaId, Pageable pageable) {
        return ResponseEntity.ok(juegoService.getAllByPlataformasId(plataformaId, pageable));
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
