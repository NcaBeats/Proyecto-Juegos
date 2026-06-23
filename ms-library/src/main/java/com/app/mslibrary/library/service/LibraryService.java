package com.app.mslibrary.library.service;

import com.app.mslibrary.library.client.ProfileClient;
import com.app.mslibrary.library.model.Library;
import com.app.mslibrary.library.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ProfileClient profileClient;

    @Transactional
    public Library getOrCreate(Long userId) {
        log.debug("Obteniendo o creando biblioteca para userId={}", userId);
        log.debug("Llamando a ProfileClient.getProfileByUserId userId={}", userId);
        profileClient.getProfileByUserId(userId);

        Library lib = libraryRepository.findById(userId)
                .orElseGet(() -> {
                    Library created = libraryRepository.save(
                            Library.builder()
                                    .userId(userId)
                                    .build()
                    );
                    log.info("Biblioteca creada para userId={}", userId);
                    return created;
                });
        log.debug("Biblioteca obtenida para userId={}", userId);
        return lib;

    }
    public Optional<Library> findByUserId(Long  userId) {
        return libraryRepository.findById(userId);
    }
}
