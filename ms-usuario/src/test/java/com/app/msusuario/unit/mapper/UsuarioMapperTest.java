package com.app.msusuario.unit.mapper;

import com.app.msusuario.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.app.msusuario.support.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioMapperTest {

    @InjectMocks
    UsuarioMapper usuarioMapper;
    @Test
    void toEntity_ValidRequest_ReturnUserEntity(){
        var result = usuarioMapper.toEntity(USER_REQUEST);
        assertNotNull(result);
        assertEquals(USER_REQUEST.nombre(),result.getNombre());
        assertEquals(USER_REQUEST.email(),result.getEmail());
        assertEquals(USER_REQUEST.saldo(),result.getSaldo());
    }
    @Test
    void toResponse_ValidEntity_ReturnUserResponse(){
        var result = usuarioMapper.toResponse(USER_ENTITY);
        assertNotNull(result);
        assertEquals(USER_ENTITY.getId(),result.id());
        assertEquals(USER_ENTITY.getNombre(),result.nombre());
        assertEquals(USER_ENTITY.getEmail(),result.email());
        assertEquals(USER_ENTITY.getSaldo(),result.saldo());
        assertEquals(USER_ENTITY.getFecha_creacion(),result.fecha_creacion());
    }

}
