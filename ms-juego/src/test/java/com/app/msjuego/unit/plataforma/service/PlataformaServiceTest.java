package com.app.msjuego.unit.plataforma.service;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.mapper.PlataformaMapper;
import com.app.msjuego.plataforma.model.Plataforma;
import com.app.msjuego.plataforma.repository.PlataformaRepository;
import com.app.msjuego.plataforma.service.PlataformaService;
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

import static com.app.msjuego.support.EstudioFactory.ID;
import static com.app.msjuego.support.PlataformaFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlataformaServiceTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    PlataformaRepository plataformaRepository;
    @Mock
    PlataformaMapper plataformaMapper;
    @InjectMocks
    PlataformaService plataformaService;

    @Test
    void findAll_ReturnPage(){
        Plataforma plataforma = createPlataformaEntityFaker();
        List<Plataforma> list = List.of(plataforma);
        Page<Plataforma> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(plataformaRepository.findAll(PAGEABLE)).thenReturn(page);
        when(plataformaMapper.toResponse(plataforma)).thenReturn(PLATAFORMA_RESPONSE);
        var result =  plataformaService.findAll(PAGEABLE);
        assertNotNull(result);
        assertEquals(PLATAFORMA_RESPONSE, result.getContent().getFirst());
        verify(plataformaRepository).findAll(PAGEABLE);
        verify(plataformaMapper).toResponse(plataforma);
    }
    @Test
    void findById_PlataformaFound_ReturnResponse() {
        Plataforma plataforma = createPlataformaEntityFaker();
        when(plataformaRepository.findById(plataforma.getId())).thenReturn(Optional.of(plataforma));
        when(plataformaMapper.toResponse(plataforma)).thenReturn(PLATAFORMA_RESPONSE);
        var result = plataformaService.findById(plataforma.getId());
        assertNotNull(result);
        assertEquals(PLATAFORMA_RESPONSE, result);
    }
    @Test
    void findById_PlataformaNotFound_ReturnException() {
        when(plataformaRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> plataformaService.findById(ID));
        verifyNoInteractions(plataformaMapper);
    }
    @Test
    void save_Success_ReturnResponse() {
        Plataforma plataforma = createPlataformaEntityFaker();
        PlataformaRequest req = createPlataformaRequestFaker();
        when(plataformaMapper.toEntity(req)).thenReturn(plataforma);
        when(plataformaRepository.save(plataforma)).thenReturn(plataforma);
        when(plataformaMapper.toResponse(plataforma)).thenReturn(PLATAFORMA_RESPONSE);
        var result = plataformaService.save(req);
        assertNotNull(result);
        assertEquals(PLATAFORMA_RESPONSE, result);
        verify(plataformaMapper).toEntity(req);
        verify(plataformaRepository).save(plataforma);
        verify(plataformaMapper).toResponse(plataforma);
    }
    @Test
    void update_PlataformaFound_ReturnResponse() {
        Plataforma plataforma = createPlataformaEntityFaker();
        PlataformaRequest req = createPlataformaRequestFaker();
        when(plataformaRepository.findById(plataforma.getId())).thenReturn(Optional.of(plataforma));
        when(plataformaRepository.save(plataforma)).thenReturn(plataforma);
        when(plataformaMapper.toResponse(plataforma)).thenReturn(PLATAFORMA_RESPONSE);
        var result = plataformaService.update(plataforma.getId(), req);
        assertNotNull(result);
        assertEquals(PLATAFORMA_RESPONSE, result);
        verify(plataformaRepository).findById(plataforma.getId());
        verify(plataformaRepository).save(plataforma);
        verify(plataformaMapper).toResponse(plataforma);
    }
    @Test
    void update_PlataformaNotFound_ReturnException() {
        when(plataformaRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> plataformaService.update(ID, PLATAFORMA_REQUEST));
        verify(plataformaRepository).findById(ID);
        verifyNoMoreInteractions(plataformaRepository);
        verifyNoInteractions(plataformaMapper);
    }
    @Test
    void deleteById_PlataformaFound_ReturnResponse() {
        Plataforma plataforma = createPlataformaEntityFaker();
        when(plataformaRepository.existsById(plataforma.getId())).thenReturn(true);
        doNothing().when(plataformaRepository).deleteById(plataforma.getId());
        plataformaService.delete(plataforma.getId());
        verify(plataformaRepository).deleteById(plataforma.getId());
    }
    @Test
    void deleteById_PlataformaNotFound_ReturnException() {
        when(plataformaRepository.existsById(ID)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> plataformaService.delete(ID));

    }
}
