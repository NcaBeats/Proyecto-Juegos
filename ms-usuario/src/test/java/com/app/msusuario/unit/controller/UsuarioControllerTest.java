package com.app.msusuario.unit.controller;

import com.app.msusuario.assembler.UsuarioAssembler;
import com.app.msusuario.controller.UsuarioController;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;

import static com.app.msusuario.support.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);


    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioAssembler usuarioAssembler;
    @Mock
    private PagedResourcesAssembler<UsuarioResponse> pagedResourcesAssembler;
    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void findAll_ReturnsOk() {
        var page = Page.<UsuarioResponse>empty();
        when(usuarioService.findAll(PAGEABLE)).thenReturn(page);
        var pagedModel = PagedModel.<EntityModel<UsuarioResponse>>empty();
        when(pagedResourcesAssembler.toModel(page, usuarioAssembler)).thenReturn(pagedModel);

        var result = usuarioController.findAll(PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }
    @Test
    void findById_UserFound_ReturnUserResponse() {
        var entityModel = EntityModel.of(USER_RESPONSE);
        when(usuarioService.findById(USER_RESPONSE.id())).thenReturn(USER_RESPONSE);
        when(usuarioAssembler.toModel(USER_RESPONSE)).thenReturn(entityModel);

        var result = usuarioController.findById(USER_RESPONSE.id());

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE, result.getBody().getContent());
    }
    @Test
    void findByEmail_UserFound_ReturnUserResponse() {
        var entityModel = EntityModel.of(USER_RESPONSE);
        String email = USER_RESPONSE.email();
        when(usuarioService.findByEmail(email)).thenReturn(USER_RESPONSE);
        when(usuarioAssembler.toModel(USER_RESPONSE)).thenReturn(entityModel);
        var result = usuarioController.findByEmail(email);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE, result.getBody().getContent());
    }
    @Test
    void findByNombre_UserFound_ReturnUserResponse() {
        var entityModel = EntityModel.of(USER_RESPONSE);
        String nombre = USER_RESPONSE.nombre();
        when(usuarioService.findByNombre(nombre)).thenReturn(USER_RESPONSE);
        when(usuarioAssembler.toModel(USER_RESPONSE)).thenReturn(entityModel);
        var result = usuarioController.findByNombre(nombre);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE, result.getBody().getContent());
    }
    @Test
    void save_SaveUser_ReturnUserResponse() {
        var entityModel = EntityModel.of(USER_RESPONSE);
        when(usuarioService.save(USER_REQUEST)).thenReturn(USER_RESPONSE);
        when(usuarioAssembler.toModel(USER_RESPONSE)).thenReturn(entityModel);

        var result = usuarioController.save(USER_REQUEST);

        assertEquals(201, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE, result.getBody().getContent());
    }
    @Test
    void update_UpdateUser_ReturnUserResponse() {
        var entityModel = EntityModel.of(USER_RESPONSE);
        when(usuarioService.update(USER_RESPONSE.id(), USER_REQUEST)).thenReturn(USER_RESPONSE);
        when(usuarioAssembler.toModel(USER_RESPONSE)).thenReturn(entityModel);

        var result = usuarioController.update(USER_RESPONSE.id(), USER_REQUEST);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(USER_RESPONSE, result.getBody().getContent());
    }
    @Test
    void update_UpdateBalance_ReturnNoContent() {
        BigDecimal monto = BigDecimal.valueOf(100);
        doNothing().when(usuarioService).restarSaldo(1L, monto);

        var result = usuarioController.updateBalance(1L, monto);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }
    @Test
    void delete_DeleteUser_ReturnNoContent() {
        doNothing().when(usuarioService).delete(1L);

        var result = usuarioController.delete(1L);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }

}
