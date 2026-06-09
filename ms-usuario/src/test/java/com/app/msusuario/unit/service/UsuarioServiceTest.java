package com.app.msusuario.unit.service;

import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.mapper.UsuarioMapper;
import com.app.msusuario.model.Usuario;
import com.app.msusuario.repository.UsuarioRepository;
import com.app.msusuario.service.UsuarioService;
import com.app.msusuario.support.UsuarioFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    private static final Pageable PAGEABLE = PageRequest.of(0, 10);
    private static final Usuario USER_ENTITY = UsuarioFactory.createUsuario();
    private static final UsuarioResponse USER_RESPONSE = UsuarioFactory.createUsuarioResponse();

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    UsuarioMapper usuarioMapper;
    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void findAll() {

        List<Usuario> listaUsuarios = List.of(USER_ENTITY);

        Page<Usuario> paginaUsuarios = new PageImpl<>(listaUsuarios,PAGEABLE,listaUsuarios.size());

        when(usuarioRepository.findAll(PAGEABLE)).thenReturn(paginaUsuarios);
        when(usuarioMapper.toResponse(USER_ENTITY)).thenReturn(USER_RESPONSE);


        var result = usuarioService.findAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1,result.getContent().size());
        assertEquals(USER_RESPONSE,result.getContent().getFirst());
        verify(usuarioMapper).toResponse(USER_ENTITY);

    }
    @Test
    void findById_UserFound_ReturnUserResponse (){

        when(usuarioRepository.findById(USER_RESPONSE.id())).thenReturn(Optional.of(USER_ENTITY));
        when(usuarioMapper.toResponse(USER_ENTITY)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findById(USER_RESPONSE.id());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);

    }
    @Test
    void findById_UserNotFound_ReturnException () {

        when(usuarioRepository.findById(USER_RESPONSE.id())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()-> usuarioService.findById(USER_RESPONSE.id()));
        verifyNoInteractions(usuarioMapper); // Verifica que no se haya utilizado el Mapper
    }
    @Test
    void findByEmail_UserFound_ReturnUserResponse () {

        when(usuarioRepository.findByEmail(USER_RESPONSE.email())).thenReturn(Optional.of(USER_ENTITY));
        when(usuarioMapper.toResponse(USER_ENTITY)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findByEmail(USER_RESPONSE.email());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);

    }
}
