package com.app.msjuego.repository;

import com.app.msjuego.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
}
