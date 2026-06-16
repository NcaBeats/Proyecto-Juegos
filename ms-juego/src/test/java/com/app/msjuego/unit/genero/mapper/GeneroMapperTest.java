package com.app.msjuego.unit.genero.mapper;

import com.app.msjuego.genero.mapper.GeneroMapper;
import com.app.msjuego.genero.model.Genero;
import org.junit.jupiter.api.Test;

import static com.app.msjuego.support.GeneroFactory.createGeneroEntity;
import static org.junit.jupiter.api.Assertions.*;
import static com.app.msjuego.support.GeneroFactory.GENERO_REQUEST;

public class GeneroMapperTest {
    GeneroMapper generoMapper = new GeneroMapper();

    @Test
    void To_Entity_ValidRequest_ReturnEntity() {
        var result = generoMapper.toEntity(GENERO_REQUEST);
        assertNotNull(result);
        assertEquals(GENERO_REQUEST.nombre(), result.getNombre());
    }
    @Test
    void To_Response_ValidEntity_ReturnResponse(){
        Genero genero = createGeneroEntity();
        var result = generoMapper.toResponse(genero);
        assertNotNull(result);
        assertEquals(genero.getId(), result.id());
        assertEquals(genero.getNombre(), result.nombre());
    }
}
