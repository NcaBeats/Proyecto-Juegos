package com.app.msjuego.plataforma.controller;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.service.PlataformaService;
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
@RequestMapping("api/v1/plataformas")
@Tag(
        name = "Plataformas",
        description = "Gestión de plataformas de videojuegos de la plataforma Nico's Games"
)
public class PlataformaController {

    private final PlataformaService plataformaService;

    @Operation(summary = "Obtener una plataforma por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma encontrada"),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponse> findById(
            @Parameter(description = "ID de la plataforma")
            @Valid @PathVariable Long id) {

        log.debug("GET /api/v1/plataformas/{}", id);
        return ResponseEntity.ok(plataformaService.findById(id));
    }

    @Operation(summary = "Obtener todas las plataformas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<Page<PlataformaResponse>> findAll(Pageable pageable) {

        log.debug("GET /api/v1/plataformas - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(plataformaService.findAll(pageable));
    }

    @Operation(summary = "Crear una nueva plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plataforma creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PlataformaResponse> save(@Valid @RequestBody PlataformaRequest request) {

        log.info("POST /api/v1/plataformas - creando plataforma");
        return ResponseEntity.status(HttpStatus.CREATED).body(plataformaService.save(request));
    }

    @Operation(summary = "Actualizar una plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponse> update(
            @Parameter(description = "ID de la plataforma")
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequest request) {

        log.info("PUT /api/v1/plataformas/{} - actualizando plataforma", id);
        return ResponseEntity.ok(plataformaService.update(id, request));
    }

    @Operation(summary = "Eliminar una plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plataforma eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la plataforma")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/plataformas/{} - eliminando plataforma", id);
        plataformaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
