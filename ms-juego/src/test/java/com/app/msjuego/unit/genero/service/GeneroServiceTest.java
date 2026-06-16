package com.app.msjuego.unit.genero.service;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.mapper.GeneroMapper;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.genero.repository.GeneroRepository;
import com.app.msjuego.genero.service.GeneroService;
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

import static com.app.msjuego.support.EstudioFactory.*;
import static com.app.msjuego.support.GeneroFactory.GENERO_REQUEST;
import static com.app.msjuego.support.GeneroFactory.GENERO_RESPONSE;
import static com.app.msjuego.support.GeneroFactory.createGeneroEntityFaker;
import static com.app.msjuego.support.GeneroFactory.createGeneroRequestFaker;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeneroServiceTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    GeneroRepository generoRepository;
    @Mock
    GeneroMapper generoMapper;
    @InjectMocks
    GeneroService generoService;

    @Test
    void findAll_ReturnPage(){
        Genero genero = createGeneroEntityFaker();
        List<Genero> list = List.of(genero);
        Page<Genero> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(generoRepository.findAll(PAGEABLE)).thenReturn(page);
        when(generoMapper.toResponse(genero)).thenReturn(GENERO_RESPONSE);
        var result =  generoService.findAll(PAGEABLE);
        assertNotNull(result);
        assertEquals(GENERO_RESPONSE, result.getContent().getFirst());
        verify(generoRepository).findAll(PAGEABLE);
        verify(generoMapper).toResponse(genero);
    }
    @Test
    void findById_GeneroFound_ReturnResponse() {
        Genero genero = createGeneroEntityFaker();
        when(generoRepository.findById(genero.getId())).thenReturn(Optional.of(genero));
        when(generoMapper.toResponse(genero)).thenReturn(GENERO_RESPONSE);
        var result = generoService.findById(genero.getId());
        assertNotNull(result);
        assertEquals(GENERO_RESPONSE, result);
    }
    @Test
    void findById_GeneroNotFound_ReturnException() {
        when(generoRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> generoService.findById(ID));
        verifyNoInteractions(generoMapper);
    }
    @Test
    void save_Success_ReturnResponse() {
        Genero genero = createGeneroEntityFaker();
        GeneroRequest req = createGeneroRequestFaker();
        when(generoMapper.toEntity(req)).thenReturn(genero);
        when(generoRepository.save(genero)).thenReturn(genero);
        when(generoMapper.toResponse(genero)).thenReturn(GENERO_RESPONSE);
        var result = generoService.save(req);
        assertNotNull(result);
        assertEquals(GENERO_RESPONSE, result);
        verify(generoMapper).toEntity(req);
        verify(generoRepository).save(genero);
        verify(generoMapper).toResponse(genero);
    }
    @Test
    void update_GeneroFound_ReturnResponse() {
        Genero genero = createGeneroEntityFaker();
        GeneroRequest req = createGeneroRequestFaker();
        when(generoRepository.findById(genero.getId())).thenReturn(Optional.of(genero));
        when(generoRepository.save(genero)).thenReturn(genero);
        when(generoMapper.toResponse(genero)).thenReturn(GENERO_RESPONSE);
        var result = generoService.update(genero.getId(), req);
        assertNotNull(result);
        assertEquals(GENERO_RESPONSE, result);
        verify(generoRepository).findById(genero.getId());
        verify(generoRepository).save(genero);
        verify(generoMapper).toResponse(genero);
    }
    @Test
    void update_GeneroNotFound_ReturnException() {
        when(generoRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> generoService.update(ID, GENERO_REQUEST));
        verify(generoRepository).findById(ID);
        verifyNoMoreInteractions(generoRepository);
        verifyNoInteractions(generoMapper);
    }
    @Test
    void deleteById_GeneroFound_ReturnResponse() {
        Genero genero = createGeneroEntityFaker();
        when(generoRepository.existsById(genero.getId())).thenReturn(true);
        doNothing().when(generoRepository).deleteById(genero.getId());
        generoService.delete(genero.getId());
        verify(generoRepository).deleteById(genero.getId());
    }
    @Test
    void deleteById_GeneroNotFound_ReturnException() {
        when(generoRepository.existsById(ID)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> generoService.delete(ID));

    }
}
