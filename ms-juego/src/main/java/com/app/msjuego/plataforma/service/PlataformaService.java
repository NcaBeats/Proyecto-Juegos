package com.app.msjuego.plataforma.service;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.mapper.PlataformaMapper;
import com.app.msjuego.plataforma.model.Plataforma;
import com.app.msjuego.plataforma.repository.PlataformaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlataformaService {
    private final PlataformaRepository plataformaRepository;
    private final PlataformaMapper plataformaMapper;

    public PlataformaResponse findById(Long id) {
        log.debug("Buscando plataforma con id: {}", id);
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada con id: " + id));
        log.info("Plataforma con id {} encontrada", id);
        return plataformaMapper.toResponse(plataforma);
    }

    public Page<PlataformaResponse> findAll(Pageable pageable) {
        log.debug("Buscando página {} de plataformas (Tamaño de página: {})", pageable.getPageNumber(), pageable.getPageSize());
        return plataformaRepository.findAll(pageable).map(plataformaMapper::toResponse);
    }

    @Transactional
    public PlataformaResponse save(PlataformaRequest request) {
        log.info("Recibiendo petición para crear plataforma");
        Plataforma plataforma = plataformaMapper.toEntity(request);
        Plataforma saved = plataformaRepository.save(plataforma);
        log.info("Plataforma creada exitosamente con id: {}", saved.getId());
        return plataformaMapper.toResponse(saved);
    }

    @Transactional
    public PlataformaResponse update(Long id, PlataformaRequest request) {
        log.info("Recibiendo petición para actualizar plataforma");
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada"));
        log.info("Plataforma con id {} encontrada", plataforma.getId());
        plataforma.update(request);
        Plataforma updated = plataformaRepository.save(plataforma);
        log.info("Plataforma con id: {} actualizada", updated.getId());
        return plataformaMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Recibiendo petición para eliminar plataforma");
        if (!plataformaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar una plataforma inexistente");
        }
        plataformaRepository.deleteById(id);
        log.info("Plataforma eliminada con id: {}", id);
    }
}
