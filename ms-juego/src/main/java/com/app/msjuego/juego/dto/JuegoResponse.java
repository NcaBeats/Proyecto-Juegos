package com.app.msjuego.juego.dto;

import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.juego.model.EstadoJuego;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder
public record JuegoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        LocalDate fechaLanzamiento,
        EstadoJuego estado,
        Instant fechaRegistro,
        EstudioResponse estudio,
        List<GeneroResponse> generos,
        List<PlataformaResponse> plataformas
) {
}
