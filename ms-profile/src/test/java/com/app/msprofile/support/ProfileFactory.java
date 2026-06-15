package com.app.msprofile.support;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.dto.external.UserResponse;
import com.app.msprofile.model.Profile;
import com.app.msprofile.model.TipoPerfil;

import java.time.Instant;

public class ProfileFactory {
    public static final Long USER_ID = 1L;
    public static final String NICKNAME = "gamer_test";
    public static final String AVATAR = "avatar.png";
    public static final String BIO = "Bio test";
    public static final TipoPerfil TIPO = TipoPerfil.PUBLICO;
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    public static final ProfileRequest PROFILE_REQUEST = ProfileRequest.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .bio(BIO)
            .tipoPerfil(TIPO)
            .build();

    public static final Profile PROFILE_ENTITY = Profile.builder()
            .userId(USER_ID)
            .nickname(NICKNAME)
            .avatar(AVATAR)
            .bio(BIO)
            .tipoPerfil(TIPO)
            .build();

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
