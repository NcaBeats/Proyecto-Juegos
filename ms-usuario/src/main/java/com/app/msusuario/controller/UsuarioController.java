package com.app.msusuario.controller;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(
        name = "Usuarios",
        description = "Gestión de usuarios de la plataforma de videojuegos"
)
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene una lista paginada de todos los usuarios registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> findAll(Pageable pageable) {
        log.debug("GET /api/v1/usuarios - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }

    @Operation(
            summary = "Buscar usuario por ID",
            description = "Obtiene la información de un usuario específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(
            @Parameter(description = "ID del usuario")
            @PathVariable Long id) {

        log.debug("GET /api/v1/usuarios/{} - obteniendo usuario", id);
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @Operation(
            summary = "Buscar usuario por email",
            description = "Obtiene la información de un usuario mediante su correo electrónico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(
            @Parameter(description = "Correo electrónico del usuario")
            @PathVariable String email) {

        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @Operation(
            summary = "Buscar usuario por nombre",
            description = "Obtiene un usuario mediante su nombre"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<UsuarioResponse> findByNombre(
            @Parameter(description = "Nombre del usuario")
            @PathVariable String nombre) {

        return ResponseEntity.ok(usuarioService.findByNombre(nombre));
    }

    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario en la plataforma"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(
            @Valid @RequestBody UsuarioRequest usuarioRequest) {

        log.info("POST /api/v1/usuarios - creando usuario email={}", usuarioRequest.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioRequest));
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @Parameter(description = "ID del usuario")
            @PathVariable Long id,

            @Valid @RequestBody UsuarioRequest usuarioRequest) {

        log.info("PUT /api/v1/usuarios/{} - actualizando usuario", id);
        return ResponseEntity.ok(usuarioService.update(id, usuarioRequest));
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del usuario")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/usuarios/{} - eliminando usuario", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Actualizar saldo",
            description = "Descuenta saldo de la cuenta de un usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Saldo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Monto inválido")
    })
    @PutMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(

            @Parameter(description = "ID del usuario")
            @PathVariable Long id,

            @Parameter(description = "Monto a descontar")
            @RequestParam BigDecimal monto) {

        log.info("PUT /api/v1/usuarios/{}/balance - restando monto={}", id, monto);
        usuarioService.restarSaldo(id, monto);
        return ResponseEntity.noContent().build();
    }
}

