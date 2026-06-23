package com.app.mslibrary.library.repository;

import com.app.mslibrary.library.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Long> {
}
