package com.app.msusuario.unit.service;

import com.app.msusuario.mapper.UsuarioMapper;
import com.app.msusuario.model.Usuario;
import com.app.msusuario.repository.UsuarioRepository;
import com.app.msusuario.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.app.msusuario.support.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    private static final Pageable PAGEABLE = PageRequest.of(0, 10);


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
    @Test
    void findByEmail_UserNotFound_ReturnException () {
        when(usuarioRepository.findByEmail(USER_RESPONSE.email())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.findByEmail(USER_RESPONSE.email()));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void findByNombre_UserFound_ReturnUserResponse () {

        when(usuarioRepository.findByNombre(USER_RESPONSE.nombre())).thenReturn(Optional.of(USER_ENTITY));
        when(usuarioMapper.toResponse(USER_ENTITY)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findByNombre(USER_RESPONSE.nombre());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);

    }
    @Test
    void findByNombre_UserNotFound_ReturnException () {
        when(usuarioRepository.findByNombre(USER_RESPONSE.nombre())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.findByNombre(USER_RESPONSE.nombre()));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void save_UserSaved_ReturnUserResponse () {
        when(usuarioMapper.toEntity(USER_REQUEST)).thenReturn(USER_ENTITY);
        when(usuarioRepository.save(USER_ENTITY)).thenReturn(USER_ENTITY);
        when(usuarioMapper.toResponse(USER_ENTITY)).thenReturn(USER_RESPONSE);

        var result = usuarioService.save(USER_REQUEST);

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);
        verify(usuarioMapper).toEntity(USER_REQUEST);
        verify(usuarioRepository).save(USER_ENTITY);
        verify(usuarioMapper).toResponse(USER_ENTITY);
    }
    @Test
    void save_NombreDuplicated_ReturnException () {
        when(usuarioMapper.toEntity(USER_REQUEST)).thenReturn(USER_ENTITY);
        when(usuarioRepository.save(USER_ENTITY)).thenThrow(new DataIntegrityViolationException("Nombre duplicado"));
        assertThrows(DataIntegrityViolationException.class,()-> usuarioService.save(USER_REQUEST));
    }
    @Test
    void save_EmailDuplicated_ReturnException () {
        when(usuarioMapper.toEntity(USER_REQUEST)).thenReturn(USER_ENTITY);
        when(usuarioRepository.save(USER_ENTITY)).thenThrow(new DataIntegrityViolationException("Email duplicado"));
        assertThrows(DataIntegrityViolationException.class,()-> usuarioService.save(USER_REQUEST));
    }
    @Test
    void update_UserUpdated_ReturnUserResponse () {
        Usuario usuarioSpy = spy(USER_ENTITY);
        when(usuarioRepository.findById(USER_RESPONSE.id())).thenReturn(Optional.of(usuarioSpy));
        when(usuarioMapper.toResponse(usuarioSpy)).thenReturn(USER_RESPONSE);

        var result = usuarioService.update(USER_RESPONSE.id(),USER_REQUEST);

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);
        verify(usuarioSpy).update(USER_REQUEST);
    }
    @Test
    void update_UserNotFound_ReturnException () {
        when(usuarioRepository.findById(USER_RESPONSE.id())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.update(USER_RESPONSE.id(),USER_REQUEST));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void delete_UserDeleted_ReturnUserResponse () {
        when(usuarioRepository.findById(USER_RESPONSE.id())).thenReturn(Optional.of(USER_ENTITY));
        usuarioService.delete(USER_RESPONSE.id());
        verify(usuarioRepository).delete(USER_ENTITY);
    }
    @Test
    void delete_UserNotFound_ReturnException() {
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.delete(ID));
        verify(usuarioRepository, never()).delete(any());
    }
    @Test
    void restarSaldo_SaldoRestado() {
        Usuario usuario = Usuario.builder()
                .id(ID).nombre(NOMBRE).email(EMAIL).saldo(BigDecimal.valueOf(500)).build();

        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));

        usuarioService.restarSaldo(ID, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(400.00), usuario.getSaldo());
    }
    @Test
    void restarSaldo_UserNotFound_ReturnException() {
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> usuarioService.restarSaldo(ID, BigDecimal.valueOf(100)));
    }
    @Test
    void restarSaldo_SaldoInsuficiente_ReturnException() {
        Usuario usuario = Usuario.builder()
                .id(ID).nombre(NOMBRE).email(EMAIL).saldo(BigDecimal.valueOf(50)).build();

        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));

        assertThrows(RuntimeException.class,
                () -> usuarioService.restarSaldo(ID, BigDecimal.valueOf(100)));
    }
}
