package com.app.msusuario.support;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.model.Usuario;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;

public class UsuarioFactory {

    public static final Long ID = 1L;
    public static final String NOMBRE = "Nico";
    public static final String EMAIL = "nico@test.com";
    public static final BigDecimal SALDO = BigDecimal.valueOf(500.00);
    public static final Instant FECHA = Instant.parse("2024-01-01T00:00:00Z");

    public static final UsuarioRequest USER_REQUEST =
            new UsuarioRequest(NOMBRE, EMAIL, SALDO);

    public static final Usuario USER_ENTITY = Usuario.builder()
            .id(ID).nombre(NOMBRE).email(EMAIL).saldo(SALDO).build();

    public static final UsuarioResponse USER_RESPONSE =
            new UsuarioResponse(ID, NOMBRE, EMAIL, SALDO, FECHA);

    private static final Faker faker = new Faker(Locale.of("es"));

    public static Usuario createUsuarioEntity() {
        return Usuario.builder()
                .id(ID).nombre(NOMBRE).email(EMAIL).saldo(SALDO).build();
    }

    public static Usuario createUsuarioEntity(Long id, String nombre, String email, BigDecimal saldo) {
        return Usuario.builder()
                .id(id).nombre(nombre).email(email).saldo(saldo).build();
    }

    public static UsuarioRequest createUsuarioRequestFaker() {
        return new UsuarioRequest(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000))
        );
    }
}
