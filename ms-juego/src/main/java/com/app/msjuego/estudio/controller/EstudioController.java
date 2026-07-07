package com.app.msjuego.estudio.controller;

import com.app.msjuego.estudio.assembler.EstudioAssembler;
import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.service.EstudioService;
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
@RequestMapping("api/v1/estudios")
@Tag(
        name = "Estudios",
        description = "Gestión de estudios desarrolladores"
)
public class EstudioController {

    private final EstudioService estudioService;
    private final EstudioAssembler estudioAssembler;
    private final PagedResourcesAssembler<EstudioResponse> pagedResourcesAssembler;

    @Operation(summary = "Obtener un estudio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EstudioResponse>> findById(
            @Parameter(description = "ID del estudio")
            @Valid @PathVariable Long id) {

        log.debug("GET /api/v1/estudios/{}", id);
        return ResponseEntity.ok(estudioAssembler.toModel(estudioService.findById(id)));
    }

    @Operation(summary = "Obtener todos los estudios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EstudioResponse>>> findAll(@ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/estudios - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(estudioService.findAll(pageable), estudioAssembler));
    }

    @Operation(summary = "Crear un nuevo estudio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudio creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<EstudioResponse>> save(@Valid @RequestBody EstudioRequest request) {

        log.info("POST /api/v1/estudios - creando estudio");
        return ResponseEntity.status(HttpStatus.CREATED).body(estudioAssembler.toModel(estudioService.save(request)));
    }

    @Operation(summary = "Actualizar un estudio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estudio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EstudioResponse>> update(
            @Parameter(description = "ID del estudio")
            @PathVariable Long id,
            @Valid @RequestBody EstudioRequest request) {

        log.info("PUT /api/v1/estudios/{} - actualizando estudio", id);
        return ResponseEntity.ok(estudioAssembler.toModel(estudioService.update(id, request)));
    }

    @Operation(summary = "Eliminar un estudio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estudio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del estudio")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/estudios/{} - eliminando estudio", id);
        estudioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
