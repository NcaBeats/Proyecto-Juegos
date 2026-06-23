package com.app.msjuego.support;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.model.Juego;
import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.model.Plataforma;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class PlataformaFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long ID = 1L;
    public static final String NOMBRE = "Genero Test";
    public static final List<Juego> JUEGOS = List.of();
    public static final Instant FECHA_REGISTRO = Instant.parse("2024-01-01T00:00:00Z");

    public static Plataforma createPlataformaEntity() {
        return Plataforma.builder()
                .id(ID)
                .nombre(NOMBRE)
                .juegos(JUEGOS)
                .fechaCreacion(FECHA_REGISTRO)
                .build();
    }
    public static Plataforma createPlataformaEntityFaker(){
        return new Plataforma(
                FAKER.number().randomNumber(),
                FAKER.company().name(),
                JUEGOS,
                FECHA_REGISTRO
        );
    }
    public static final PlataformaRequest PLATAFORMA_REQUEST =
            new PlataformaRequest(NOMBRE);

    public static PlataformaRequest createPlataformaRequestFaker() {
        return new PlataformaRequest(
                FAKER.company().name());
    }

    public static final PlataformaResponse PLATAFORMA_RESPONSE =
            new PlataformaResponse(ID, NOMBRE, FECHA_REGISTRO);
}
