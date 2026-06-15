package com.app.msjuego.unit.service;

import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.mapper.JuegoMapper;
import com.app.msjuego.juego.model.Juego;
import com.app.msjuego.juego.repository.JuegoRepository;
import com.app.msjuego.juego.service.JuegoService;
import com.app.msjuego.plataforma.model.Plataforma;
import com.app.msjuego.estudio.repository.EstudioRepository;
import com.app.msjuego.genero.repository.GeneroRepository;
import com.app.msjuego.plataforma.repository.PlataformaRepository;
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

import static com.app.msjuego.support.JuegoFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JuegoServiceTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    JuegoRepository juegoRepository;
    @Mock
    JuegoMapper juegoMapper;
    @Mock
    EstudioRepository estudioRepository;
    @Mock
    GeneroRepository generoRepository;
    @Mock
    PlataformaRepository plataformaRepository;

    @InjectMocks
    JuegoService juegoService;

    @Test
    void findById_Found_ReturnResponse() {
        when(juegoRepository.findById(ID)).thenReturn(Optional.of(JUEGO_ENTITY));
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.findById(ID);

        assertNotNull(result);
        assertEquals(JUEGO_RESPONSE, result);
    }

    @Test
    void findById_NotFound_Throw() {
        when(juegoRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> juegoService.findById(ID));
        verifyNoInteractions(juegoMapper);
    }

    @Test
    void findAll_ReturnPage() {
        List<Juego> list = List.of(JUEGO_ENTITY);
        Page<Juego> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(juegoRepository.findAll(PAGEABLE)).thenReturn(page);
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.findAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(JUEGO_RESPONSE, result.getContent().getFirst());
        verify(juegoRepository).findAll(PAGEABLE);
    }

    @Test
    void save_Success_ReturnResponse() {
        JuegoRequest req = JUEGO_REQUEST;
        Estudio estudio = createEstudio();
        List<Genero> generos = List.of(createGenero(GENERO_ID));
        List<Plataforma> plataformas = List.of(createPlataforma(PLATAFORMA_ID));

        when(estudioRepository.findById(req.estudioId())).thenReturn(Optional.of(estudio));
        when(generoRepository.findAllById(req.generoIds())).thenReturn(generos);
        when(plataformaRepository.findAllById(req.plataformaIds())).thenReturn(plataformas);
        when(juegoMapper.toEntity(req, estudio, generos, plataformas)).thenReturn(JUEGO_ENTITY);
        when(juegoRepository.save(JUEGO_ENTITY)).thenReturn(JUEGO_ENTITY);
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.save(req);

        assertNotNull(result);
        assertEquals(JUEGO_RESPONSE, result);
    }

    @Test
    void save_EstudioNotFound_Throw() {
        JuegoRequest req = JUEGO_REQUEST;
        when(estudioRepository.findById(req.estudioId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> juegoService.save(req));
    }

    @Test
    void findByNombre_Found_ReturnResponse() {
        when(juegoRepository.findByNombre(JUEGO_REQUEST.nombre())).thenReturn(Optional.of(JUEGO_ENTITY));
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.findByNombre(JUEGO_REQUEST.nombre());

        assertNotNull(result);
        assertEquals(JUEGO_RESPONSE, result);
    }

    @Test
    void findByNombre_NotFound_Throw() {
        when(juegoRepository.findByNombre(JUEGO_REQUEST.nombre())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> juegoService.findByNombre(JUEGO_REQUEST.nombre()));
    }

    @Test
    void getAllByEstudioId_ReturnsPage() {
        List<Juego> list = List.of(JUEGO_ENTITY);
        Page<Juego> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(juegoRepository.getAllByEstudioId(ESTUDIO_ID, PAGEABLE)).thenReturn(page);
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.getAllByEstudioId(ESTUDIO_ID, PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getAllByGenerosId_ReturnsPage() {
        List<Juego> list = List.of(JUEGO_ENTITY);
        Page<Juego> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(juegoRepository.getAllByGenerosId(GENERO_ID, PAGEABLE)).thenReturn(page);
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.getAllByGenerosId(GENERO_ID, PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getAllByPlataformasId_ReturnsPage() {
        List<Juego> list = List.of(JUEGO_ENTITY);
        Page<Juego> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(juegoRepository.getAllByPlataformasId(PLATAFORMA_ID, PAGEABLE)).thenReturn(page);
        when(juegoMapper.toResponse(JUEGO_ENTITY)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.getAllByPlataformasId(PLATAFORMA_ID, PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void update_Success_ReturnResponse() {
        JuegoRequest req = JUEGO_REQUEST;
        Juego entity = createJuegoEntity();
        Estudio estudio = createEstudio();
        List<Genero> generos = List.of(createGenero(GENERO_ID));
        List<Plataforma> plataformas = List.of(createPlataforma(PLATAFORMA_ID));

        when(juegoRepository.findById(ID)).thenReturn(Optional.of(entity));
        when(estudioRepository.findById(req.estudioId())).thenReturn(Optional.of(estudio));
        when(generoRepository.findAllById(req.generoIds())).thenReturn(generos);
        when(plataformaRepository.findAllById(req.plataformaIds())).thenReturn(plataformas);
        when(juegoRepository.save(entity)).thenReturn(entity);
        when(juegoMapper.toResponse(entity)).thenReturn(JUEGO_RESPONSE);

        var result = juegoService.update(ID, req);

        assertNotNull(result);
        assertEquals(JUEGO_RESPONSE, result);
    }

    @Test
    void update_NotFound_Throw() {
        when(juegoRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> juegoService.update(ID, JUEGO_REQUEST));
    }

    @Test
    void delete_Success() {
        when(juegoRepository.existsById(ID)).thenReturn(true);
        doNothing().when(juegoRepository).deleteById(ID);

        juegoService.delete(ID);

        verify(juegoRepository).deleteById(ID);
    }

    @Test
    void delete_NotFound_Throw() {
        when(juegoRepository.existsById(ID)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> juegoService.delete(ID));
    }

}
