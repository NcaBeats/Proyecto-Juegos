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
    public static Usuario createUsuario() {
        return Usuario.builder()
                .nombre(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .saldo(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)))
                .build();
    }
    public static UsuarioResponse createUsuarioResponse() {
        return new UsuarioResponse(
                faker.number().randomNumber(),
                faker.name().firstName(),
                faker.internet().emailAddress(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)),
                Instant.now()
        );
    }
    public static UsuarioRequest  createUsuarioRequest() {
        return new UsuarioRequest(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000))
        );
    }
}
