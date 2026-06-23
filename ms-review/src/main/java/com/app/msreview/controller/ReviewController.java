package com.app.msreview.controller;

import com.app.msreview.dto.ReviewRequest;
import com.app.msreview.dto.ReviewResponse;
import com.app.msreview.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/reviews")
@Tag(
        name = "Reviews",
        description = "Gestión de reseñas de videojuegos de la plataforma Nico's Games"
)
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Obtener todas las reviews de un juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<ReviewResponse>> findAllByJuegoId(
            @Parameter(description = "ID del juego")
            @PathVariable Long gameId) {

        log.debug("GET /api/v1/reviews/game/{} - obteniendo reviews", gameId);
        return ResponseEntity.ok(reviewService.findAllByJuegoId(gameId));
    }

    @Operation(summary = "Obtener todas las reviews de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> findAllByUserId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/reviews/user/{} - obteniendo reviews", userId);
        return ResponseEntity.ok(reviewService.findAllByUserId(userId));
    }

    @Operation(summary = "Crear una nueva review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ReviewResponse> save(
            @Valid @RequestBody ReviewRequest reviewRequest) {

        log.info("POST /api/v1/reviews - creando review userId={} juegoId={}",
                reviewRequest.userId(),
                reviewRequest.juegoId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.save(reviewRequest));
    }

    @Operation(summary = "Actualizar una review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Review no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(
            @Parameter(description = "ID de la review")
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest reviewRequest) {

        log.info("PUT /api/v1/reviews/{} - actualizando review userId={}",
                id,
                reviewRequest.userId());

        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }

    @Operation(summary = "Eliminar una review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Review no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la review")
            @PathVariable Long id) {

        log.info("DELETE /api/v1/reviews/{} - eliminando review", id);
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
