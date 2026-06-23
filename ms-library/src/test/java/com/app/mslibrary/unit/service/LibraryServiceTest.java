package com.app.mslibrary.unit.service;

import com.app.mslibrary.library.client.ProfileClient;
import com.app.mslibrary.library.model.Library;
import com.app.mslibrary.library.repository.LibraryRepository;
import com.app.mslibrary.library.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.app.mslibrary.support.LibraryFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    LibraryRepository libraryRepository;
    @Mock
    ProfileClient profileClient;

    @InjectMocks
    LibraryService libraryService;

    @Test
    void getOrCreate_WhenLibraryExists_ReturnsExisting() {
        Library library = createLibraryEntity();

        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(PROFILE_RESPONSE);
        when(libraryRepository.findById(USER_ID)).thenReturn(Optional.of(library));

        var result = libraryService.getOrCreate(USER_ID);

        assertNotNull(result);
        assertEquals(USER_ID, result.getUserId());
        verify(profileClient).getProfileByUserId(USER_ID);
        verify(libraryRepository).findById(USER_ID);
        verify(libraryRepository, never()).save(any());
    }

    @Test
    void getOrCreate_WhenLibraryNotExists_CreatesNew() {
        Library library = createLibraryEntity();

        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(PROFILE_RESPONSE);
        when(libraryRepository.findById(USER_ID)).thenReturn(Optional.empty());
        when(libraryRepository.save(any(Library.class))).thenReturn(library);

        var result = libraryService.getOrCreate(USER_ID);

        assertNotNull(result);
        assertEquals(USER_ID, result.getUserId());
        verify(profileClient).getProfileByUserId(USER_ID);
        verify(libraryRepository).findById(USER_ID);
        verify(libraryRepository).save(any(Library.class));
    }

    @Test
    void findByUserId_WhenExists() {
        Library library = createLibraryEntity();

        when(libraryRepository.findById(USER_ID)).thenReturn(Optional.of(library));

        var result = libraryService.findByUserId(USER_ID);

        assertTrue(result.isPresent());
        assertEquals(USER_ID, result.get().getUserId());
    }

    @Test
    void findByUserId_WhenNotExists() {
        when(libraryRepository.findById(USER_ID)).thenReturn(Optional.empty());

        var result = libraryService.findByUserId(USER_ID);

        assertTrue(result.isEmpty());
    }
}
