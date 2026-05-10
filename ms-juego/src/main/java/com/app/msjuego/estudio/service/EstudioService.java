package com.app.msjuego.estudio.service;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.mapper.EstudioMapper;
import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.estudio.repository.EstudioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstudioService {
    private final EstudioRepository estudioRepository;
    private final EstudioMapper estudioMapper;

    public EstudioResponse findById(Long id) {
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado con id: " + id));
        return estudioMapper.toResponse(estudio);
    }

    public Page<EstudioResponse> findAll(Pageable pageable) {
        return estudioRepository.findAll(pageable).map(estudioMapper::toResponse);
    }

    @Transactional
    public EstudioResponse save(EstudioRequest request) {
        Estudio estudio = estudioMapper.toEntity(request);
        return estudioMapper.toResponse(estudioRepository.save(estudio));
    }

    @Transactional
    public EstudioResponse update(Long id, EstudioRequest request) {
        Estudio estudio = estudioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        estudio.update(request);
        return estudioMapper.toResponse(estudioRepository.save(estudio));
    }

    @Transactional
    public void delete(Long id) {
        if (!estudioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un estudio inexistente");
        }
        estudioRepository.deleteById(id);
    }
}