package com.app.msjuego.unit.estudio.service;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.mapper.EstudioMapper;
import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.estudio.repository.EstudioRepository;
import com.app.msjuego.estudio.service.EstudioService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudioServiceTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    EstudioRepository estudioRepository;
    @Mock
    EstudioMapper estudioMapper;
    @InjectMocks
    EstudioService estudioService;

    @Test
    void findAll_ReturnPage(){
        Estudio estudio = createEstudioEntityFaker();
        List<Estudio> list = List.of(estudio);
        Page<Estudio> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(estudioRepository.findAll(PAGEABLE)).thenReturn(page);
        when(estudioMapper.toResponse(estudio)).thenReturn(ESTUDIO_RESPONSE);
        var result =  estudioService.findAll(PAGEABLE);
        assertNotNull(result);
        assertEquals(ESTUDIO_RESPONSE, result.getContent().getFirst());
        verify(estudioRepository).findAll(PAGEABLE);
        verify(estudioMapper).toResponse(estudio);
    }
    @Test
    void findById_EstudioFound_ReturnResponse() {
        Estudio estudio = createEstudioEntityFaker();
        when(estudioRepository.findById(estudio.getId())).thenReturn(Optional.of(estudio));
        when(estudioMapper.toResponse(estudio)).thenReturn(ESTUDIO_RESPONSE);
        var result = estudioService.findById(estudio.getId());
        assertNotNull(result);
        assertEquals(ESTUDIO_RESPONSE, result);
    }
    @Test
    void findById_EstudioNotFound_ReturnException() {
        when(estudioRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> estudioService.findById(ID));
        verifyNoInteractions(estudioMapper);
    }
    @Test
    void save_Success_ReturnResponse() {
        Estudio estudio = createEstudioEntityFaker();
        EstudioRequest req = createEstudioRequestFaker();
        when(estudioMapper.toEntity(req)).thenReturn(estudio);
        when(estudioRepository.save(estudio)).thenReturn(estudio);
        when(estudioMapper.toResponse(estudio)).thenReturn(ESTUDIO_RESPONSE);
        var result = estudioService.save(req);
        assertNotNull(result);
        assertEquals(ESTUDIO_RESPONSE, result);
        verify(estudioMapper).toEntity(req);
        verify(estudioRepository).save(estudio);
        verify(estudioMapper).toResponse(estudio);
    }
    @Test
    void update_EstudioFound_ReturnResponse() {
        Estudio estudio = createEstudioEntityFaker();
        EstudioRequest req = createEstudioRequestFaker();
        when(estudioRepository.findById(estudio.getId())).thenReturn(Optional.of(estudio));
        when(estudioRepository.save(estudio)).thenReturn(estudio);
        when(estudioMapper.toResponse(estudio)).thenReturn(ESTUDIO_RESPONSE);
        var result = estudioService.update(estudio.getId(), req);
        assertNotNull(result);
        assertEquals(ESTUDIO_RESPONSE, result);
        verify(estudioRepository).findById(estudio.getId());
        verify(estudioRepository).save(estudio);
        verify(estudioMapper).toResponse(estudio);
    }
    @Test
    void update_EstudioNotFound_ReturnException() {
        when(estudioRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> estudioService.update(ID, ESTUDIO_REQUEST));
        verify(estudioRepository).findById(ID);
        verifyNoMoreInteractions(estudioRepository);
        verifyNoInteractions(estudioMapper);
    }
    @Test
    void deleteById_EstudioFound_ReturnResponse() {
        Estudio estudio = createEstudioEntityFaker();
        when(estudioRepository.existsById(estudio.getId())).thenReturn(true);
        doNothing().when(estudioRepository).deleteById(estudio.getId());
        estudioService.delete(estudio.getId());
        verify(estudioRepository).deleteById(estudio.getId());
    }
    @Test
    void deleteById_EstudioNotFound_ReturnException() {
        when(estudioRepository.existsById(ID)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> estudioService.delete(ID));

    }
}
