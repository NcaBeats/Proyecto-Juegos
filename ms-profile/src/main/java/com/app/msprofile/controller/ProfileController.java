package com.app.msprofile.controller;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.service.ProfileService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/profiles")
@Tag(
        name = "Perfiles",
        description = "Gestión de perfiles de usuario de la plataforma Nico's Games"
)
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Obtener todos los perfiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<Page<ProfileResponse>> findAll(Pageable pageable) {

        log.debug("GET /api/v1/profiles - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(profileService.findAll(pageable));
    }

    @Operation(summary = "Obtener un perfil por ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> findById(
            @Parameter(description = "ID del usuario")
            @PathVariable long userId) {

        log.debug("GET /api/v1/profiles/{} - obteniendo perfil", userId);
        return ResponseEntity.ok(profileService.findById(userId));
    }

    @Operation(summary = "Buscar perfil por nickname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<ProfileResponse> buscar(
            @Parameter(description = "Nickname del usuario")
            @PathVariable String nickname) {

        log.debug("GET /api/v1/profiles/nickname/{} - buscando perfil por nickname", nickname);
        return ResponseEntity.ok(profileService.findByNickname(nickname));
    }

    @Operation(summary = "Crear un nuevo perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProfileResponse> save(
            @Valid @RequestBody ProfileRequest request) {

        log.info("POST /api/v1/profiles - creando perfil userId={}", request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(request));
    }

    @Operation(summary = "Actualizar un perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @PutMapping
    public ResponseEntity<ProfileResponse> update(
            @Valid @RequestBody ProfileRequest request) {

        log.info("PUT /api/v1/profiles - actualizando perfil userId={}", request.userId());
        return ResponseEntity.ok(profileService.update(request));
    }

    @Operation(summary = "Eliminar un perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.info("DELETE /api/v1/profiles/{} - eliminando perfil", userId);
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
