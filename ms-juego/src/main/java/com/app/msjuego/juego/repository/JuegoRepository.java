package com.app.msjuego.juego.repository;

import com.app.msjuego.juego.model.Juego;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    Optional<Juego> findByNombre(String nombre);
    Optional<Juego> findByEstudioId(Long id);
    Page<Juego>
}
