package com.app.msusuario.support;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.model.Usuario;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;

public class UsuarioFactory {
    private static final Faker faker = new Faker(Locale.of("es"));
    public static final Long ID = 1L;
    public static final String NOMBRE = "User";
    public static final String EMAIL = "user@test.com";
    public static final BigDecimal SALDO = BigDecimal.valueOf(500.00);
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    // Entidad Mutable
    public static Usuario createUsuarioEntity() {
        return Usuario.builder()
                .id(ID)
                .nombre(NOMBRE)
                .email(EMAIL)
                .saldo(SALDO)
                .build();
    }
    public static Usuario createUsuarioEntityInvalidAmount(){
        return Usuario.builder()
                .id(ID)
                .nombre(NOMBRE)
                .email(EMAIL)
                .saldo(BigDecimal.valueOf(50.00))
                .build();
    }
    // Entidad con datos faker
    public static Usuario createUsuarioEntityFaker() {
        return Usuario.builder()
                .id(faker.number().randomNumber())
                .nombre(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .saldo(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)))
                .build();
    }

    // Request Inmutable
    public static final UsuarioRequest USER_REQUEST =
            new UsuarioRequest(NOMBRE, EMAIL, SALDO);

    // Request con datos faker
    public static UsuarioRequest createUsuarioRequestFaker() {
        return new UsuarioRequest(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500))
        );
    }

    // Response Inmutable
    public static final UsuarioResponse USER_RESPONSE =
            new UsuarioResponse(ID, NOMBRE, EMAIL, SALDO, FECHA);
}
