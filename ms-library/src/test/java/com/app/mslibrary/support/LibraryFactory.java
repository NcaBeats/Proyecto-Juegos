package com.app.mslibrary.support;

import com.app.mslibrary.library.dto.external.ProfileResponse;
import com.app.mslibrary.library.model.Library;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class LibraryFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long USER_ID = 1L;
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");
    public static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse(USER_ID, "User");

    public static Library createLibraryEntity() {
        return Library.builder()
                .userId(USER_ID)
                .fechaCreacion(FECHA)
                .build();
    }

    public static Library createLibraryEntityFaker() {
        return Library.builder()
                .userId(FAKER.number().randomNumber())
                .fechaCreacion(Instant.now())
                .build();
    }
}
