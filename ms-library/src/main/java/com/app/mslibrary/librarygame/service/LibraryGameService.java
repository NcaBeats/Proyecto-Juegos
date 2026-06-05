package com.app.mslibrary.librarygame.service;

import com.app.mslibrary.library.dto.LibraryResponse;
import com.app.mslibrary.library.model.Library;
import com.app.mslibrary.library.service.LibraryService;
import com.app.mslibrary.librarygame.client.JuegoClient;
import com.app.mslibrary.librarygame.mapper.LibraryGameMapper;
import com.app.mslibrary.librarygame.model.LibraryGame;
import com.app.mslibrary.librarygame.repository.LibraryGameRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LibraryGameService {
    private final LibraryGameRepository libraryGameRepository;
    private final LibraryGameMapper libraryGameMapper;
    private final LibraryService libraryService;
    private final JuegoClient juegoClient;

    public LibraryResponse findAllByUserId (Long userId) {
        log.debug("Obteniendo biblioteca completa para userId={}", userId);
        Library library= libraryService.getOrCreate(userId);
        var games = libraryService.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Library no encontrada"))
                .getGames()
                .stream()
                .map(lg -> libraryGameMapper.toResponse(
                        lg,
                        juegoClient.findById(lg.getGameId())
                ))
                .collect(Collectors.toSet());
        log.debug("Se encontraron {} juegos en la biblioteca de userId={}", games.size(), userId);
        return LibraryResponse.builder()
                .userId(userId)
                .games(games)
                .fechaCreacion(library.getFechaCreacion())
                .build();
    }

    @Transactional
    public void addGames(Long userId, List<Long> gameIds) {
        log.info("Añadiendo juegos a la biblioteca userId={} gameIds={}", userId, gameIds);

        Library library = libraryService.getOrCreate(userId);

        gameIds.forEach(gameId -> {

            if (libraryGameRepository.existsByLibraryUserIdAndGameId(userId, gameId)) {
                throw new EntityExistsException("El juego con ID " + gameId + " ya está en la biblioteca");
            }

            LibraryGame libraryGame = LibraryGame.builder()
                    .gameId(gameId)
                    .library(library)
                    .build();

            libraryGameRepository.save(libraryGame);
            log.info("Juego añadido a la biblioteca userId={} gameId={}", userId, gameId);
        });
    }

    public boolean gameExists (Long userId, Long gameId) {
        return libraryGameRepository.existsByLibraryUserIdAndGameId(userId, gameId);
    }

    @Transactional
    public void deleteGame (Long userId, Long gameId) {
        log.info("Eliminando juego de la biblioteca userId={} gameId={}", userId, gameId);
        LibraryGame libraryGame = libraryGameRepository.findByLibraryUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("El juego no se encuentra en la biblioteca"));
        libraryGameRepository.delete(libraryGame);
        log.info("Juego eliminado de la biblioteca userId={} gameId={}", userId, gameId);
    }
}
