package com.example.mslibrary.library.controller;

import com.example.mslibrary.library.dto.LibraryResponse;
import com.example.mslibrary.librarygame.service.LibraryGameService;
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
public class LibraryController {
    private final LibraryGameService libraryGameService;

    @GetMapping("/{userId}")
    public ResponseEntity<LibraryResponse> getAllByUserId(@PathVariable Long userId) {
        log.debug("GET /api/v1/library/{} - obteniendo biblioteca", userId);
        return ResponseEntity.ok(libraryGameService.findAllByUserId(userId));
    }

    @PostMapping("/{userId}/games")
    public ResponseEntity<Void> addGames(@PathVariable Long userId,@Valid @RequestBody List<Long> gameIds) {
        log.info("POST /api/v1/library/{}/games - añadiendo juegos: {}", userId, gameIds);
        libraryGameService.addGames(userId, gameIds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/game/{gameId}/exists")
    public ResponseEntity<Boolean> gameExists(Long userId, Long gameId) {
        log.debug("GET /api/v1/library/{}/game/{}/exists - comprobando existencia", userId, gameId);
        return ResponseEntity.ok(libraryGameService.gameExists(userId, gameId));
    }

    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long userId, @PathVariable Long gameId) {
        log.info("DELETE /api/v1/library/{}/{} - eliminando juego", userId, gameId);
        libraryGameService.deleteGame(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}
