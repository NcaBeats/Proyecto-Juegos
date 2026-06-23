package com.app.msusuario.unit.service;

import com.app.msusuario.dto.UsuarioRequest;
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
    void findAll_ReturnPage() {
        Usuario usuario = createUsuarioEntityFaker();
        List<Usuario> listaUsuarios = List.of(usuario);

        Page<Usuario> paginaUsuarios = new PageImpl<>(listaUsuarios,PAGEABLE,listaUsuarios.size());

        when(usuarioRepository.findAll(PAGEABLE)).thenReturn(paginaUsuarios);
        when(usuarioMapper.toResponse(usuario)).thenReturn(USER_RESPONSE);


        var result = usuarioService.findAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1,result.getContent().size());
        assertEquals(USER_RESPONSE,result.getContent().getFirst());
        verify(usuarioMapper).toResponse(usuario);

    }
    @Test
    void findById_UserFound_ReturnUserResponse (){
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findById(usuario.getId());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);
    }
    @Test
    void findById_UserNotFound_ReturnException () {

        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()-> usuarioService.findById(ID));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void findByEmail_UserFound_ReturnUserResponse () {
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findByEmail(usuario.getEmail());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);

    }
    @Test
    void findByEmail_UserNotFound_ReturnException () {
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.findByEmail(EMAIL));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void findByNombre_UserFound_ReturnUserResponse () {
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioRepository.findByNombre(usuario.getNombre())).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(USER_RESPONSE);

        var result = usuarioService.findByNombre(usuario.getNombre());

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);

    }
    @Test
    void findByNombre_UserNotFound_ReturnException () {
        when(usuarioRepository.findByNombre(NOMBRE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.findByNombre(NOMBRE));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void save_UserSaved_ReturnUserResponse () {
        UsuarioRequest userRequest = createUsuarioRequestFaker();
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioMapper.toEntity(userRequest)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(USER_RESPONSE);

        var result = usuarioService.save(userRequest);

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);
        verify(usuarioMapper).toEntity(userRequest);
        verify(usuarioRepository).save(usuario);
        verify(usuarioMapper).toResponse(usuario);
    }
    @Test
    void save_NombreDuplicated_ReturnException () {
        UsuarioRequest userRequest = createUsuarioRequestFaker();
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioMapper.toEntity(userRequest)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenThrow(new DataIntegrityViolationException("Nombre duplicado"));
        assertThrows(DataIntegrityViolationException.class,()-> usuarioService.save(userRequest));
    }
    @Test
    void save_EmailDuplicated_ReturnException () {
        UsuarioRequest userRequest = createUsuarioRequestFaker();
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioMapper.toEntity(userRequest)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenThrow(new DataIntegrityViolationException("Email duplicado"));
        assertThrows(DataIntegrityViolationException.class,()-> usuarioService.save(userRequest));
    }
    @Test
    void update_UserUpdated_ReturnUserResponse () {
        Usuario usuario = createUsuarioEntity();
        Usuario usuarioSpy = spy(usuario);
        UsuarioRequest userRequest = createUsuarioRequestFaker();
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuarioSpy));
        when(usuarioMapper.toResponse(usuarioSpy)).thenReturn(USER_RESPONSE);

        var result = usuarioService.update(usuario.getId(),userRequest);

        assertNotNull(result);
        assertEquals(USER_RESPONSE,result);
        verify(usuarioSpy).update(userRequest);
    }
    @Test
    void update_UserNotFound_ReturnException () {
        UsuarioRequest userRequest = createUsuarioRequestFaker();
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> usuarioService.update(ID,userRequest));
        verifyNoInteractions(usuarioMapper);
    }
    @Test
    void delete_UserDeleted_ReturnUserResponse () {
        Usuario usuario = createUsuarioEntityFaker();
        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));
        usuarioService.delete(ID);
        verify(usuarioRepository).delete(usuario);
    }
    @Test
    void delete_UserNotFound_ReturnException() {
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.delete(ID));
        verify(usuarioRepository, never()).delete(any());
    }
    @Test
    void subtractBalance_ValidAmount_BalanceDecremented() {
        Usuario usuario = createUsuarioEntity();

        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));

        usuarioService.restarSaldo(ID, BigDecimal.valueOf(100));

        assertEquals(0, usuario.getSaldo().compareTo(BigDecimal.valueOf(400)));
    }
    @Test
    void subtractBalance_UserNotFound_ReturnException() {
        when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> usuarioService.restarSaldo(ID, BigDecimal.valueOf(100)));
    }
    @Test
    void subtractBalance_InvalidAmount_ReturnException() {
        Usuario usuario = createUsuarioEntityInvalidAmount();

        when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class,
                () -> usuarioService.restarSaldo(ID, BigDecimal.valueOf(100)));
    }
}
