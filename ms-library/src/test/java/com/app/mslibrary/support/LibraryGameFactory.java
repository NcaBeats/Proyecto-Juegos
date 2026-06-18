package com.app.mslibrary.support;

import com.app.mslibrary.librarygame.dto.LibraryGameResponse;
import com.app.mslibrary.librarygame.dto.external.JuegoResponse;
import com.app.mslibrary.librarygame.model.LibraryGame;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class LibraryGameFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final Long GAME_ID = 1L;
    public static final String GAME_NAME = "Game";
    private static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    public static final JuegoResponse JUEGO_RESPONSE = new JuegoResponse(GAME_ID, GAME_NAME);

    public static final LibraryGameResponse LIBRARY_GAME_RESPONSE = LibraryGameResponse.builder()
            .id(ID)
            .gameId(GAME_ID)
            .gameName(GAME_NAME)
            .fechaCreacion(FECHA)
            .build();

    public static LibraryGame createLibraryGameEntity() {
        return LibraryGame.builder()
                .id(ID)
                .gameId(GAME_ID)
                .build();
    }

    public static LibraryGame createLibraryGameEntityFaker() {
        return LibraryGame.builder()
                .id(FAKER.number().randomNumber())
                .gameId(FAKER.number().randomNumber())
                .build();
    }
}
