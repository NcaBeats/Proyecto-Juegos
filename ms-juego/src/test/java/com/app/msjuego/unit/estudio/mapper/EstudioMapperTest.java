package com.app.msjuego.unit.estudio.mapper;

import com.app.msjuego.estudio.mapper.EstudioMapper;
import com.app.msjuego.estudio.model.Estudio;
import org.junit.jupiter.api.Test;

import static com.app.msjuego.support.EstudioFactory.ESTUDIO_REQUEST;
import static com.app.msjuego.support.EstudioFactory.createEstudioEntity;
import static org.junit.jupiter.api.Assertions.*;

public class EstudioMapperTest {
    EstudioMapper estudioMapper = new EstudioMapper();

    @Test
    void ToEntity_ValidRequest_ReturnEntity() {
        var result = estudioMapper.toEntity(ESTUDIO_REQUEST);
        assertNotNull(result);
        assertEquals(ESTUDIO_REQUEST.nombre(), result.getNombre());
    }
    @Test
    void ToResponse_ValidEntity_ReturnResponse() {
        Estudio estudio = createEstudioEntity();
        var result = estudioMapper.toResponse(estudio);
        assertNotNull(result);
        assertEquals(estudio.getId(), result.id());
        assertEquals(estudio.getNombre(), result.nombre());
    }
}
