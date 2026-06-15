package com.app.msjuego.unit.plataforma.mapper;

import com.app.msjuego.plataforma.mapper.PlataformaMapper;
import com.app.msjuego.plataforma.model.Plataforma;
import org.junit.jupiter.api.Test;

import static com.app.msjuego.support.PlataformaFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlataformaMapperTest {
    PlataformaMapper plataformaMapper = new PlataformaMapper();

    @Test
    void To_Entity_ValidRequest_ReturnEntity() {
        var result = plataformaMapper.toEntity(PLATAFORMA_REQUEST);
        assertNotNull(result);
        assertEquals(PLATAFORMA_REQUEST.nombre(), result.getNombre());
    }
    @Test
    void To_Response_ValidEntity_ReturnResponse(){
        Plataforma plataforma = createPlataformaEntity();
        var result = plataformaMapper.toResponse(plataforma);
        assertNotNull(result);
        assertEquals(plataforma.getId(), result.id());
        assertEquals(plataforma.getNombre(), result.nombre());
    }
}
