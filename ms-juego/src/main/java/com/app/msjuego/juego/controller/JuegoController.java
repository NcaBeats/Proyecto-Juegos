package com.app.msjuego.juego.controller;

import com.app.msjuego.juego.assembler.JuegoAssembler;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.service.JuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/juegos")
@Tag(
        name = "Juegos",
        description = "Gestión de videojuegos"
)
public class JuegoController {

    private final JuegoService juegoService;
    private final JuegoAssembler juegoAssembler;
    private final PagedResourcesAssembler<JuegoResponse> pagedResourcesAssembler;

    @Operation(summary = "Obtener todos los juegos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<JuegoResponse>>> findAll(@ParameterObject Pageable pageable) {
        log.debug("GET /api/v1/juegos - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(juegoService.findAll(pageable), juegoAssembler));
    }

    @Operation(summary = "Obtener un juego por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego encontrado"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<JuegoResponse>> findById(
            @Parameter(description = "ID del juego")
            @Valid @PathVariable Long id) {

        log.debug("GET /api/v1/juegos/{}", id);
        return ResponseEntity.ok(juegoAssembler.toModel(juegoService.findById(id)));
    }

    @Operation(summary = "Obtener juegos por estudio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juegos obtenidos correctamente")
    })
    @GetMapping("/estudio/{estudioId}")
    public ResponseEntity<PagedModel<EntityModel<JuegoResponse>>> getAllByEstudioId(
            @Parameter(description = "ID del estudio")
            @PathVariable Long estudioId,
            @ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/juegos/estudio/{} - página: {} tamaño: {}", estudioId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(juegoService.getAllByEstudioId(estudioId, pageable), juegoAssembler));
    }

    @Operation(summary = "Obtener juegos por género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juegos obtenidos correctamente")
    })
    @GetMapping("/genero/{generoId}")
    public ResponseEntity<PagedModel<EntityModel<JuegoResponse>>> getAllByGenerosId(
            @Parameter(description = "ID del género")
            @PathVariable Long generoId,
            @ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/juegos/genero/{} - página: {} tamaño: {}", generoId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(juegoService.getAllByGenerosId(generoId, pageable), juegoAssembler));
    }

    @Operation(summary = "Obtener juegos por plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juegos obtenidos correctamente")
    })
    @GetMapping("/plataforma/{plataformaId}")
    public ResponseEntity<PagedModel<EntityModel<JuegoResponse>>> getAllByPlataformasId(
            @Parameter(description = "ID de la plataforma")
            @PathVariable Long plataformaId,
            @ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/juegos/plataforma/{} - página: {} tamaño: {}", plataformaId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(juegoService.getAllByPlataformasId(plataformaId, pageable), juegoAssembler));
    }

    @Operation(summary = "Buscar juego por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego encontrado"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<EntityModel<JuegoResponse>> buscarPorNombre(
            @Parameter(description = "Nombre del juego")
            @RequestParam String nombre) {

        log.debug("GET /api/v1/juegos/buscar nombre={}", nombre);
        return ResponseEntity.ok(juegoAssembler.toModel(juegoService.findByNombre(nombre)));
    }

    @Operation(summary = "Crear un nuevo juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juego creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<JuegoResponse>> save(@Valid @RequestBody JuegoRequest request) {
        log.info("POST /api/v1/juegos - creando juego");
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoAssembler.toModel(juegoService.save(request)));
    }

    @Operation(summary = "Actualizar un juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juego actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<JuegoResponse>> update(
            @Parameter(description = "ID del juego")
            @PathVariable Long id,
            @Valid @RequestBody JuegoRequest request) {

        log.info("PUT /api/v1/juegos/{} - actualizando juego", id);
        return ResponseEntity.ok(juegoAssembler.toModel(juegoService.update(id, request)));
    }

    @Operation(summary = "Eliminar un juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Juego eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del juego")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/juegos/{} - eliminando juego", id);
        juegoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}