package com.app.msjuego.support;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.model.Juego;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class GeneroFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long ID = 1L;
    public static final String NOMBRE = "Genero Test";
    public static final List<Juego> JUEGOS = List.of();
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");

    public static Genero createGeneroEntity() {
        return Genero.builder()
                .id(ID)
                .nombre(NOMBRE)
                .juegos(JUEGOS)
                .fechaCreacion(FECHA_REGISTRO)
                .build();
    }
    public static Genero createGeneroEntityFaker(){
        return new Genero(
                FAKER.number().randomNumber(),
                FAKER.company().name(),
                JUEGOS,
                FECHA_REGISTRO
        );
    }
    public static final GeneroRequest GENERO_REQUEST =
            new GeneroRequest(NOMBRE);

    public static GeneroRequest createGeneroRequestFaker() {
        return new GeneroRequest(
                FAKER.company().name());
    }

    public static final GeneroResponse GENERO_RESPONSE =
            new GeneroResponse(ID, NOMBRE, FECHA_REGISTRO);
}
