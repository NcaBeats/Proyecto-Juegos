package com.app.msjuego.support;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.juego.model.Juego;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class EstudioFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long ID = 1L;
    public static final String NOMBRE = "Estudio Test";
    public static final List<Juego> JUEGOS = List.of();
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");

    public static Estudio createEstudioEntity() {
        return Estudio.builder()
                .id(ID)
                .nombre(NOMBRE)
                .juegos(JUEGOS)
                .fechaCreacion(FECHA_REGISTRO)
                .build();
    }
    public static Estudio createEstudioEntityFaker(){
        return new Estudio(
                FAKER.number().randomNumber(),
                FAKER.company().name(),
                JUEGOS,
                FECHA_REGISTRO
        );
    }
    public static final EstudioRequest ESTUDIO_REQUEST =
            new EstudioRequest(NOMBRE);

    public static EstudioRequest createEstudioRequestFaker() {
        return new EstudioRequest(
                FAKER.company().name());
    }

    public static final EstudioResponse ESTUDIO_RESPONSE =
            new EstudioResponse(ID, NOMBRE, FECHA_REGISTRO);
}
