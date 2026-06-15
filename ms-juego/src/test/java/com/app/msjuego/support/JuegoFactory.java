package com.app.msjuego.support;

import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.model.EstadoJuego;
import com.app.msjuego.juego.model.Juego;
import com.app.msjuego.plataforma.model.Plataforma;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class JuegoFactory {

    public static final Long ID = 1L;
    public static final String NOMBRE = "Juego Test";
    public static final String DESCRIPCION = "Descripcion test";
    public static final BigDecimal PRECIO = BigDecimal.valueOf(59.99);
    public static final LocalDate FECHA = LocalDate.of(2024,1,1);
    public static final EstadoJuego ESTADO = EstadoJuego.ACTIVO;
    public static final Long ESTUDIO_ID = 1L;
    public static final Long GENERO_ID = 1L;
    public static final Long PLATAFORMA_ID = 1L;
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");

    public static final JuegoRequest JUEGO_REQUEST = new JuegoRequest(
            NOMBRE,
            DESCRIPCION,
            PRECIO,
            FECHA,
            ESTADO,
            ESTUDIO_ID,
            List.of(GENERO_ID),
            List.of(PLATAFORMA_ID)
    );

    public static final Juego JUEGO_ENTITY = Juego.builder()
            .id(ID)
            .nombre(NOMBRE)
            .descripcion(DESCRIPCION)
            .precio(PRECIO)
            .fechaLanzamiento(FECHA)
            .estado(ESTADO)
            .build();

    public static final JuegoResponse JUEGO_RESPONSE = JuegoResponse.builder()
            .id(ID)
            .nombre(NOMBRE)
            .descripcion(DESCRIPCION)
            .precio(PRECIO)
            .fechaLanzamiento(FECHA)
            .estado(ESTADO)
            .fechaRegistro(FECHA_REGISTRO)
            .build();

    public static Estudio createEstudio() {
        return Estudio.builder().id(ESTUDIO_ID).nombre("Estudio Test").build();
    }

    public static Genero createGenero(Long id) {
        return Genero.builder().id(id).nombre("Genero " + id).build();
    }

    public static Plataforma createPlataforma(Long id) {
        return Plataforma.builder().id(id).nombre("Plataforma " + id).build();
    }

    public static Juego createJuegoEntity() {
        Juego j = Juego.builder()
                .id(ID)
                .nombre(NOMBRE)
                .descripcion(DESCRIPCION)
                .precio(PRECIO)
                .fechaLanzamiento(FECHA)
                .estado(ESTADO)
                .estudio(createEstudio())
                .build();
        j.setGeneros(List.of(createGenero(GENERO_ID)));
        j.setPlataformas(List.of(createPlataforma(PLATAFORMA_ID)));
        return j;
    }

}
