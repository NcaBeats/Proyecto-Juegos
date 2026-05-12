package com.example.mslibrary.librarygame.repository;

import com.example.mslibrary.librarygame.model.LibraryGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryGameRepository extends JpaRepository<LibraryGame,Long> {
}
