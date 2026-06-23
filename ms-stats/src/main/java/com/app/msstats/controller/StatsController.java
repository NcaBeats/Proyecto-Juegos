package com.app.msstats.controller;

import com.app.msstats.dto.GameStatsResponse;
import com.app.msstats.dto.UserStatsResponse;
import com.app.msstats.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/stats")
@Tag(
        name = "Estadísticas",
        description = "Estadísticas generales de usuarios y videojuegos"
)
public class StatsController {

    private final StatsService statsService;

    @Operation(summary = "Obtener estadísticas de un videojuego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/game/{gameId}")
    public ResponseEntity<GameStatsResponse> getStatsByGame(

            @Parameter(description = "ID del videojuego")
            @PathVariable Long gameId) {

        log.debug("GET /api/v1/stats/game/{} - obteniendo stats de juego",
                gameId);

        return ResponseEntity.ok(
                statsService.getGameStats(gameId)
        );
    }

    @Operation(summary = "Obtener estadísticas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserStatsResponse> getStatsByUser(

            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/stats/user/{} - obteniendo stats de usuario",
                userId);

        return ResponseEntity.ok(
                statsService.getUserStats(userId)
        );
    }
}

