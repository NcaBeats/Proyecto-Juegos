package com.app.msusuario.controller;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/usuarios - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        log.debug("GET /api/v1/usuarios/{} - obteniendo usuario", id);
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<UsuarioResponse> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(usuarioService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        log.info("POST /api/v1/usuarios - creando usuario email={}", usuarioRequest.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequest usuarioRequest) {
        log.info("PUT /api/v1/usuarios/{} - actualizando usuario", id);
        return ResponseEntity.ok(usuarioService.update(id, usuarioRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/usuarios/{} - eliminando usuario", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable Long id,@RequestParam BigDecimal monto) {
        log.info("PUT /api/v1/usuarios/{}/balance - restando monto={}", id, monto);
        usuarioService.restarSaldo(id, monto);
        return ResponseEntity.noContent().build();
    }
}

