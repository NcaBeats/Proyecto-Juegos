package com.example.mslibrary.library.service;

import com.example.mslibrary.library.client.ProfileClient;
import com.example.mslibrary.library.model.Library;
import com.example.mslibrary.library.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ProfileClient profileClient;

    @Transactional
    public Library getOrCreate(Long userId) {
        profileClient.getProfileByUserId(userId);

        return libraryRepository.findById(userId)
                .orElseGet(() -> libraryRepository.save(
                        Library.builder()
                                .userId(userId)
                                .build()
                ));

    }
    public Optional<Library> findByUserId(Long  userId) {
        return libraryRepository.findById(userId);
    }
}
