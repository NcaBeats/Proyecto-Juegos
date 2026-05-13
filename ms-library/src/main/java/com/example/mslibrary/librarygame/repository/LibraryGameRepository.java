package com.example.mslibrary.librarygame.repository;

import com.example.mslibrary.librarygame.model.LibraryGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryGameRepository extends JpaRepository<LibraryGame,Long> {
    boolean existsByLibraryUserIdAndGameId(Long userId, Long gameId);
    Optional<LibraryGame> findByLibraryUserIdAndGameId(Long userId, Long gameId);
}
