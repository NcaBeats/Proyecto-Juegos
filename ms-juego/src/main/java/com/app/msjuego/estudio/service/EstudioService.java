package com.app.msjuego.estudio.service;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.mapper.EstudioMapper;
import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.estudio.repository.EstudioRepository;
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
public class EstudioService {
    private final EstudioRepository estudioRepository;
    private final EstudioMapper estudioMapper;

    public EstudioResponse findById(Long id) {
        log.info("Buscando estudio con id: {}",id);
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado con id: " + id));
        log.info("Estudio con id {} encontrado", id);
        return estudioMapper.toResponse(estudio);
    }

    public Page<EstudioResponse> findAll(Pageable pageable) {
        log.info("Buscando página {} de estudios (Tamaño de página: {})",pageable.getPageNumber(),pageable.getPageSize());
        return estudioRepository.findAll(pageable).map(estudioMapper::toResponse);
    }

    @Transactional
    public EstudioResponse save(EstudioRequest request) {
        log.info("Recibiendo petición para crear estudio");
        Estudio estudio = estudioMapper.toEntity(request);
        Estudio estudioSaved = estudioRepository.save(estudio);
        log.info("Estudio creado exitosamente con id: {}",estudioSaved.getId());
        return estudioMapper.toResponse(estudioSaved);
    }

    @Transactional
    public EstudioResponse update(Long id, EstudioRequest request) {
        log.info("Recibiendo petición para actualizar estudio");
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        log.info("Estudio con id {} encontrado",estudio.getId());
        estudio.update(request);
        log.info("Estudio con id: {} actualizado",estudio.getId());
        return estudioMapper.toResponse(estudioRepository.save(estudio));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Recibiendo petición parar eliminar estudio");
        if (!estudioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un estudio inexistente");
        }
        log.info("Estudio encontrado con id: {}",id);
        estudioRepository.deleteById(id);
        log.info("Estudio eliminado con id: {}",id);
    }
}