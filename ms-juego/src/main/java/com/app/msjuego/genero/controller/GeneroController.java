package com.app.msjuego.genero.controller;

import com.app.msjuego.genero.assembler.GeneroAssembler;
import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.service.GeneroService;
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
@RequestMapping("api/v1/generos")
@Tag(
        name = "Géneros",
        description = "Gestión de géneros de videojuegos de la plataforma Nico's Games"
)
public class GeneroController {

    private final GeneroService generoService;
    private final GeneroAssembler generoAssembler;
    private final PagedResourcesAssembler<GeneroResponse> pagedResourcesAssembler;

    @Operation(summary = "Obtener un género por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género encontrado"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GeneroResponse>> findById(
            @Parameter(description = "ID del género")
            @Valid @PathVariable Long id) {

        log.debug("GET /api/v1/generos/{}", id);
        return ResponseEntity.ok(generoAssembler.toModel(generoService.findById(id)));
    }

    @Operation(summary = "Obtener todos los géneros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<GeneroResponse>>> findAll(@ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/generos - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(generoService.findAll(pageable), generoAssembler));
    }

    @Operation(summary = "Crear un nuevo género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Género creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<GeneroResponse>> save(@Valid @RequestBody GeneroRequest request) {

        log.info("POST /api/v1/generos - creando género");
        return ResponseEntity.status(HttpStatus.CREATED).body(generoAssembler.toModel(generoService.save(request)));
    }

    @Operation(summary = "Actualizar un género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<GeneroResponse>> update(
            @Parameter(description = "ID del género")
            @PathVariable Long id,
            @Valid @RequestBody GeneroRequest request) {

        log.info("PUT /api/v1/generos/{} - actualizando género", id);
        return ResponseEntity.ok(generoAssembler.toModel(generoService.update(id, request)));
    }

    @Operation(summary = "Eliminar un género")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Género eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del género")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/generos/{} - eliminando género", id);
        generoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
