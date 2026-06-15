package com.app.msjuego.support;

import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.model.EstadoJuego;
import com.app.msjuego.juego.model.Juego;
import net.datafaker.Faker;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static com.app.msjuego.support.EstudioFactory.*;
import static com.app.msjuego.support.GeneroFactory.*;
import static com.app.msjuego.support.PlataformaFactory.*;

public class JuegoFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final String NOMBRE = "Juego Test";
    public static final String DESCRIPCION = "Descripcion test";
    public static final BigDecimal PRECIO = BigDecimal.valueOf(59.99);
    public static final LocalDate FECHA = LocalDate.of(2024,1,1);
    public static final EstadoJuego ESTADO = EstadoJuego.ACTIVO;
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");

    public static final Long ESTUDIO_ID = 1L;
    public static final Long GENERO_ID = 1L;
    public static final Long PLATAFORMA_ID = 1L;

    // Entidad Mutable
    public static Juego createJuegoEntity() {
        return Juego.builder()
                .id(ID)
                .nombre(NOMBRE)
                .descripcion(DESCRIPCION)
                .precio(PRECIO)
                .fechaLanzamiento(FECHA)
                .estado(ESTADO)
                .estudio(createEstudioEntity())
                .generos(List.of(createGeneroEntity()))
                .plataformas(List.of(createPlataformaEntity()))
                .build();
    }
    // Entidad Mutable Con Datos Faker
    public static Juego createJuegoEntityFaker(){
        return Juego.builder()
                .id(FAKER.number().randomNumber())
                .nombre(FAKER.videoGame().title())
                .descripcion(FAKER.lorem().sentence())
                .precio(BigDecimal.valueOf(FAKER.number().randomDouble(2, 50, 100)))
                .fechaLanzamiento(LocalDate.now().minusYears(FAKER.number().numberBetween(1, 10)))
                .estado(ESTADO)
                .estudio(createEstudioEntity())
                .generos(List.of(createGeneroEntity()))
                .plataformas(List.of(createPlataformaEntity()))
                .build();
    }
    // Request Inmutable
    public static final JuegoRequest JUEGO_REQUEST =
            new JuegoRequest(
            NOMBRE,
            DESCRIPCION,
            PRECIO,
            FECHA,
            ESTADO,
            ESTUDIO_ID,
            List.of(GENERO_ID),
            List.of(PLATAFORMA_ID)
    );
    public static JuegoRequest  createJuegoRequestFaker(){
        return new JuegoRequest(
                FAKER.videoGame().title(),
                FAKER.lorem().sentence(),
                BigDecimal.valueOf(FAKER.number().randomDouble(2, 50, 100)),
                LocalDate.now().minusYears(FAKER.number().numberBetween(1, 10)),
                ESTADO,
                ESTUDIO_ID,
                List.of(GENERO_ID),
                List.of(PLATAFORMA_ID)
        );
    }
    // Response Inmutable
    public static final JuegoResponse JUEGO_RESPONSE =
            new JuegoResponse(
            ID,
            NOMBRE,
            DESCRIPCION,
            PRECIO,
            FECHA,
            ESTADO,
            FECHA_REGISTRO,
            ESTUDIO_RESPONSE,
            List.of(GENERO_RESPONSE),
            List.of(PLATAFORMA_RESPONSE)

    );
}
