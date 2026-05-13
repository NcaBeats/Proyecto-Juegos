package com.example.mslibrary.library.controller;

import com.example.mslibrary.library.dto.LibraryResponse;
import com.example.mslibrary.librarygame.dto.LibraryGameRequest;
import com.example.mslibrary.librarygame.dto.LibraryGameResponse;
import com.example.mslibrary.librarygame.service.LibraryGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/library")
public class LibraryController {
    private final LibraryGameService libraryGameService;

    @GetMapping("/{userId}")
    public ResponseEntity<LibraryResponse> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(libraryGameService.findAllByUserId(userId));
    }

    @PostMapping("/{userId}/games")
    public ResponseEntity<Void> addGames(@PathVariable Long userId, @RequestBody List<Long> gameIds) {
        libraryGameService.addGames(userId, gameIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/game/{gameId}/exists")
    public ResponseEntity<Boolean> gameExists(Long userId, Long gameId) {
        return ResponseEntity.ok(libraryGameService.gameExists(userId, gameId));
    }

    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long userId, @PathVariable Long gameId) {
        libraryGameService.deleteGame(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}
