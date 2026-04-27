package com.app.msjuego.repository;

import com.app.msjuego.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    Optional<Juego> findByNombre(String nombre);
}
