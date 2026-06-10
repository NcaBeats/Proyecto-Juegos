package com.app.msstats.controller;

import com.app.msstats.dto.GameStatsResponse;
import com.app.msstats.dto.UserStatsResponse;
import com.app.msstats.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/stats")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/game/{gameId}")
    public ResponseEntity<GameStatsResponse>  getStatsByGame(@PathVariable Long gameId) {
        log.debug("GET /api/v1/stats/game/{} - obteniendo stats de juego", gameId);
        return ResponseEntity.ok(statsService.getGameStats(gameId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserStatsResponse>  getStatsByUser(@PathVariable Long userId) {
        log.debug("GET /api/v1/stats/user/{} - obteniendo stats de usuario", userId);
        return ResponseEntity.ok(statsService.getUserStats(userId));
    }
}

