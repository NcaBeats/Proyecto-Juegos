package com.app.mslibrary.unit.service;

import com.app.mslibrary.library.model.Library;
import com.app.mslibrary.library.service.LibraryService;
import com.app.mslibrary.librarygame.client.JuegoClient;
import com.app.mslibrary.librarygame.mapper.LibraryGameMapper;
import com.app.mslibrary.librarygame.model.LibraryGame;
import com.app.mslibrary.librarygame.repository.LibraryGameRepository;
import com.app.mslibrary.librarygame.service.LibraryGameService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.app.mslibrary.support.LibraryGameFactory;
import static com.app.mslibrary.support.LibraryFactory.*;
import static com.app.mslibrary.support.LibraryGameFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryGameServiceTest {

    @Mock
    LibraryGameRepository libraryGameRepository;
    @Mock
    LibraryGameMapper libraryGameMapper;
    @Mock
    LibraryService libraryService;
    @Mock
    JuegoClient juegoClient;

    @InjectMocks
    LibraryGameService libraryGameService;

    @Test
    void findAllByUserId_ReturnsLibraryResponse() {
        LibraryGame game = createLibraryGameEntity();
        Library library = Library.builder()
                .userId(USER_ID)
                .fechaCreacion(FECHA)
                .games(Set.of(game))
                .build();

        when(libraryService.getOrCreate(USER_ID)).thenReturn(library);
        when(libraryService.findByUserId(USER_ID)).thenReturn(Optional.of(library));
        when(juegoClient.findById(game.getGameId())).thenReturn(JUEGO_RESPONSE);
        when(libraryGameMapper.toResponse(game, JUEGO_RESPONSE)).thenReturn(LIBRARY_GAME_RESPONSE);

        var result = libraryGameService.findAllByUserId(USER_ID);

        assertNotNull(result);
        assertEquals(USER_ID, result.userId());
        assertEquals(1, result.games().size());
        assertTrue(result.games().contains(LIBRARY_GAME_RESPONSE));
        verify(libraryService).getOrCreate(USER_ID);
        verify(libraryService).findByUserId(USER_ID);
        verify(juegoClient).findById(game.getGameId());
    }

    @Test
    void addGames_Success() {
        Library library = createLibraryEntity();

        when(libraryService.getOrCreate(USER_ID)).thenReturn(library);
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(false);

        libraryGameService.addGames(USER_ID, List.of(GAME_ID));

        verify(libraryGameRepository).save(any(LibraryGame.class));
    }

    @Test
    void addGames_MultipleGames() {
        Library library = createLibraryEntity();
        Long gameId2 = LibraryGameFactory.FAKER.number().randomNumber();

        when(libraryService.getOrCreate(USER_ID)).thenReturn(library);
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(false);
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, gameId2)).thenReturn(false);

        libraryGameService.addGames(USER_ID, List.of(GAME_ID, gameId2));

        verify(libraryGameRepository, times(2)).save(any(LibraryGame.class));
    }

    @Test
    void addGames_WhenGameAlreadyExists_ThrowsEntityExistsException() {
        Library library = createLibraryEntity();

        when(libraryService.getOrCreate(USER_ID)).thenReturn(library);
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> libraryGameService.addGames(USER_ID, List.of(GAME_ID)));

        verify(libraryGameRepository, never()).save(any());
    }

    @Test
    void gameExists_ReturnsTrue() {
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(true);

        var result = libraryGameService.gameExists(USER_ID, GAME_ID);

        assertTrue(result);
    }

    @Test
    void gameExists_ReturnsFalse() {
        when(libraryGameRepository.existsByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(false);

        var result = libraryGameService.gameExists(USER_ID, GAME_ID);

        assertFalse(result);
    }

    @Test
    void deleteGame_Success() {
        LibraryGame game = createLibraryGameEntity();

        when(libraryGameRepository.findByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(Optional.of(game));

        libraryGameService.deleteGame(USER_ID, GAME_ID);

        verify(libraryGameRepository).delete(game);
    }

    @Test
    void deleteGame_WhenNotFound_ThrowsEntityNotFoundException() {
        when(libraryGameRepository.findByLibraryUserIdAndGameId(USER_ID, GAME_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> libraryGameService.deleteGame(USER_ID, GAME_ID));

        verify(libraryGameRepository, never()).delete(any());
    }
}
