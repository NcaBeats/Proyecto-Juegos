package com.app.msjuego.unit.mapper;

import com.app.msjuego.juego.mapper.JuegoMapper;
import com.app.msjuego.juego.mapper.JuegoMapperImpl;
import com.app.msjuego.juego.model.Juego;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.app.msjuego.support.JuegoFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class JuegoMapperTest {

    JuegoMapper juegoMapper = new JuegoMapperImpl();

    @Test
    void toEntity_ValidRequest_ReturnEntity() {
        var result = juegoMapper.toEntity(JUEGO_REQUEST, createEstudio(), List.of(createGenero(GENERO_ID)), List.of(createPlataforma(PLATAFORMA_ID)));
        assertNotNull(result);
        assertEquals(JUEGO_REQUEST.nombre(), result.getNombre());
        assertEquals(JUEGO_REQUEST.descripcion(), result.getDescripcion());
        assertEquals(JUEGO_REQUEST.precio(), result.getPrecio());
    }

    @Test
    void toResponse_ValidEntity_ReturnResponse() {
        Juego juego = createJuegoEntity();
        var result = juegoMapper.toResponse(juego);
        assertNotNull(result);
        assertEquals(juego.getId(), result.id());
        assertEquals(juego.getNombre(), result.nombre());
        assertEquals(juego.getDescripcion(), result.descripcion());
    }
}
