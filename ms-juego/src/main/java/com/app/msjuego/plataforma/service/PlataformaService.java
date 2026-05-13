package com.app.msjuego.plataforma.service;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.mapper.PlataformaMapper;
import com.app.msjuego.plataforma.model.Plataforma;
import com.app.msjuego.plataforma.repository.PlataformaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlataformaService {
    private final PlataformaRepository plataformaRepository;
    private final PlataformaMapper plataformaMapper;

    public PlataformaResponse findById(Long id) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada con id: " + id));
        return plataformaMapper.toResponse(plataforma);
    }

    public Page<PlataformaResponse> findAll(Pageable pageable) {
        return plataformaRepository.findAll(pageable).map(plataformaMapper::toResponse);
    }

    @Transactional
    public PlataformaResponse save(PlataformaRequest request) {
        Plataforma plataforma = plataformaMapper.toEntity(request);
        return plataformaMapper.toResponse(plataformaRepository.save(plataforma));
    }

    @Transactional
    public PlataformaResponse update(Long id, PlataformaRequest request) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada"));
        plataforma.update(request);
        return plataformaMapper.toResponse(plataformaRepository.save(plataforma));
    }

    @Transactional
    public void delete(Long id) {
        if (!plataformaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar una plataforma inexistente");
        }
        plataformaRepository.deleteById(id);
    }
}