package com.app.msjuego.juego.repository;

import com.app.msjuego.juego.model.Juego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    Optional<Juego> findByNombre(String nombre);
    Page<Juego> findByEstudioId(Long id,Pageable pageable);
    Page<Juego> getAllByEstudioId(Long id, Pageable pageable);
    Page<Juego> getAllByGenerosId(Long id, Pageable pageable);
    Page<Juego> getAllByPlataformasId(Long id, Pageable pageable);
}
