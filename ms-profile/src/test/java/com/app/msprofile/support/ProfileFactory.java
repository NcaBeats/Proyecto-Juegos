package com.app.msprofile.support;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.dto.external.UserResponse;
import com.app.msprofile.model.Profile;
import com.app.msprofile.model.TipoPerfil;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class ProfileFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));
    public static final Long USER_ID = 1L;
    public static final String NICKNAME = "gamer_test";
    public static final String AVATAR = "avatar.png";
    public static final String BIO = "Bio test";
    public static final TipoPerfil TIPO = TipoPerfil.PUBLICO;
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");


    public static Profile createProfileEntity(){
        return Profile.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .bio(BIO)
            .tipoPerfil(TIPO)
            .fechaRegistro(FECHA)
            .build();
    }

    public static Profile createProfileEntityFaker(){
        return Profile.builder()
            .userId(FAKER.number().randomNumber())
            .nickname(FAKER.name().firstName())
            .avatar(FAKER.internet().url())
            .bio(FAKER.lorem().sentence())
            .tipoPerfil(TIPO)
            .fechaRegistro(FECHA)
            .build();
    }

    public static final ProfileRequest PROFILE_REQUEST = ProfileRequest.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .bio(BIO)
            .tipoPerfil(TIPO)
            .build();

    public static ProfileRequest createProfileRequestFaker(){
        return new ProfileRequest(
            FAKER.number().randomNumber(),
            FAKER.name().firstName(),
            FAKER.internet().url(),
            FAKER.lorem().sentence(),
            TIPO
        );
    }

    public static final ProfileResponse PROFILE_RESPONSE = ProfileResponse.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .bio(BIO)
            .fecha_registro(FECHA)
            .username("User")
            .email("user@test.com")
            .build();

    public static final UserResponse USER_RESPONSE = UserResponse.builder()
            .id(USER_ID)
            .nombre("User")
            .email("user@test.com")
            .build();

}
