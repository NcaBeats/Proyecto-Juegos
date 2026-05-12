package com.example.mslibrary.library.repository;

import com.example.mslibrary.library.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Long> {
}
