package com.app.mslibrary.library.controller;

import com.app.mslibrary.library.dto.LibraryResponse;
import com.app.mslibrary.librarygame.service.LibraryGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/library")
@Tag(
        name = "Biblioteca",
        description = "Gestión de bibliotecas de videojuegos de la plataforma Nico's Games"
)
public class LibraryController {

    private final LibraryGameService libraryGameService;

    @Operation(summary = "Obtener la biblioteca de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<LibraryResponse> getAllByUserId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/library/{} - obteniendo biblioteca", userId);

        return ResponseEntity.ok(
                libraryGameService.findAllByUserId(userId)
        );
    }

    @Operation(summary = "Agregar juegos a la biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Juegos agregados correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/{userId}/games")
    public ResponseEntity<Void> addGames(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,

            @Valid @RequestBody List<Long> gameIds) {

        log.info("POST /api/v1/library/{}/games - añadiendo juegos: {}",
                userId,
                gameIds);

        libraryGameService.addGames(userId, gameIds);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Comprobar si un juego existe en la biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprobación realizada correctamente")
    })
    @GetMapping("/{userId}/game/{gameId}/exists")
    public ResponseEntity<Boolean> gameExists(

            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,

            @Parameter(description = "ID del juego")
            @PathVariable Long gameId) {

        log.debug("GET /api/v1/library/{}/game/{}/exists - comprobando existencia",
                userId,
                gameId);

        return ResponseEntity.ok(
                libraryGameService.gameExists(userId, gameId)
        );
    }

    @Operation(summary = "Eliminar un juego de la biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Juego eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Void> deleteGame(

            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,

            @Parameter(description = "ID del juego")
            @PathVariable Long gameId) {

        log.info("DELETE /api/v1/library/{}/{} - eliminando juego",
                userId,
                gameId);

        libraryGameService.deleteGame(userId, gameId);

        return ResponseEntity.noContent().build();
    }
}
