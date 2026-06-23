package com.app.msusuario.unit.controller;

import com.app.msusuario.controller.UsuarioController;
import com.app.msusuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void findAll_ReturnsOk() {
        when(usuarioService.findAll(PAGEABLE)).thenReturn(Page.empty());

        var result = usuarioController.findAll(PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(0, result.getBody().getTotalElements());
    }
    @Test
    void findById_UserFound_ReturnUserResponse() {
        when(usuarioService.findById(USER_RESPONSE.id())).thenReturn(USER_RESPONSE);

        var result = usuarioController.findById(USER_RESPONSE.id());

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE,result.getBody());
    }
    @Test
    void findByEmail_UserFound_ReturnUserResponse() {
        String email = USER_RESPONSE.email();
        when(usuarioService.findByEmail(email)).thenReturn(USER_RESPONSE);
        var result = usuarioController.findByEmail(email);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE,result.getBody());
    }
    @Test
    void findByNombre_UserFound_ReturnUserResponse() {
        String nombre = USER_RESPONSE.nombre();
        when(usuarioService.findByNombre(nombre)).thenReturn(USER_RESPONSE);
        var result = usuarioController.findByNombre(nombre);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE,result.getBody());
    }
    @Test
    void save_SaveUser_ReturnUserResponse() {
        when(usuarioService.save(USER_REQUEST)).thenReturn(USER_RESPONSE);

        var result = usuarioController.save(USER_REQUEST);

        assertEquals(201, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(USER_RESPONSE,result.getBody());
    }
    @Test
    void update_UpdateUser_ReturnUserResponse() {
        when(usuarioService.update(USER_RESPONSE.id(), USER_REQUEST)).thenReturn(USER_RESPONSE);

        var result = usuarioController.update(USER_RESPONSE.id(), USER_REQUEST);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(USER_RESPONSE,result.getBody());
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
