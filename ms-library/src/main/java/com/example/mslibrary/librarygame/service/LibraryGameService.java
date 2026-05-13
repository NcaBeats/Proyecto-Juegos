package com.example.mslibrary.librarygame.service;

import com.example.mslibrary.library.client.ProfileClient;
import com.example.mslibrary.library.dto.LibraryResponse;
import com.example.mslibrary.library.model.Library;
import com.example.mslibrary.library.service.LibraryService;
import com.example.mslibrary.librarygame.client.JuegoClient;
import com.example.mslibrary.librarygame.dto.LibraryGameRequest;
import com.example.mslibrary.librarygame.dto.LibraryGameResponse;
import com.example.mslibrary.librarygame.dto.external.JuegoResponse;
import com.example.mslibrary.librarygame.mapper.LibraryGameMapper;
import com.example.mslibrary.librarygame.model.LibraryGame;
import com.example.mslibrary.librarygame.repository.LibraryGameRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryGameService {
    private final LibraryGameRepository libraryGameRepository;
    private final LibraryGameMapper libraryGameMapper;
    private final LibraryService libraryService;
    private final ProfileClient profileClient;
    private final JuegoClient juegoClient;

    public LibraryResponse findAllByUserId (Long userId) {
        profileClient.getProfileByUserId(userId);
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
        return LibraryResponse.builder()
                .userId(userId)
                .games(games)
                .fechaCreacion(library.getFechaCreacion())
                .build();
    }
//    @Transactional
//    public LibraryGameResponse addGame (Long userId, LibraryGameRequest gameRequest) {
//        if (libraryGameRepository.existsByLibraryUserIdAndGameId(userId, gameRequest.gameId())) {
//            throw new IllegalStateException("El juego ya está en la biblioteca");
//        }
//
//        Library library = libraryService.getOrCreate(userId);
//        JuegoResponse juego = juegoClient.findById(gameRequest.gameId());
//        LibraryGame libraryGame = libraryGameMapper.toEntity(gameRequest, library);
//        libraryGameRepository.save(libraryGame);
//        return libraryGameMapper.toResponse(libraryGame, juego);
//    }

    @Transactional
    public void addGames(Long userId, List<Long> gameIds) {

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
        });
    }

    public boolean gameExists (Long userId, Long gameId) {
        return libraryGameRepository.existsByLibraryUserIdAndGameId(userId, gameId);
    }

    @Transactional
    public void deleteGame (Long userId, Long gameId) {
        LibraryGame libraryGame = libraryGameRepository.findByLibraryUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("El juego no se encuentra en la biblioteca"));
        libraryGameRepository.delete(libraryGame);
    }
}
