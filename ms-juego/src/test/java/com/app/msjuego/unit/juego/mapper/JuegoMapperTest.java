package com.app.msjuego.unit.juego.mapper;

import com.app.msjuego.juego.mapper.JuegoMapper;
import com.app.msjuego.juego.mapper.JuegoMapperImpl;
import com.app.msjuego.juego.model.Juego;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.app.msjuego.support.EstudioFactory.createEstudioEntity;
import static com.app.msjuego.support.GeneroFactory.createGeneroEntity;
import static com.app.msjuego.support.JuegoFactory.*;
import static com.app.msjuego.support.PlataformaFactory.createPlataformaEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JuegoMapperTest {

    @InjectMocks
    JuegoMapper juegoMapper;

    @Test
    void toEntity_ValidRequest_ReturnEntity() {
        var result = juegoMapper.toEntity(JUEGO_REQUEST, createEstudioEntity(), List.of(createGeneroEntity()), List.of(createPlataformaEntity()));
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
