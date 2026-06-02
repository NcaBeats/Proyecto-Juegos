package com.app.msjuego.genero.service;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.mapper.GeneroMapper;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.genero.repository.GeneroRepository;
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
public class GeneroService {
    private final GeneroRepository generoRepository;
    private final GeneroMapper generoMapper;

    public GeneroResponse findById(Long id) {
        log.debug("Buscando género con id: {}", id);
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Género no encontrado con id: " + id));
        log.info("Género con id {} encontrado", id);
        return generoMapper.toResponse(genero);
    }

    public Page<GeneroResponse> findAll(Pageable pageable) {
        log.debug("Buscando página {} de géneros (Tamaño de página: {})", pageable.getPageNumber(), pageable.getPageSize());
        return generoRepository.findAll(pageable).map(generoMapper::toResponse);
    }

    @Transactional
    public GeneroResponse save(GeneroRequest request) {
        log.info("Recibiendo petición para crear género");
        Genero genero = generoMapper.toEntity(request);
        Genero saved = generoRepository.save(genero);
        log.info("Género creado exitosamente con id: {}", saved.getId());
        return generoMapper.toResponse(saved);
    }

    @Transactional
    public GeneroResponse update(Long id, GeneroRequest request) {
        log.info("Recibiendo petición para actualizar género");
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Género no encontrado"));
        log.info("Género con id {} encontrado", genero.getId());
        genero.update(request);
        Genero updated = generoRepository.save(genero);
        log.info("Género con id: {} actualizado", updated.getId());
        return generoMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Recibiendo petición para eliminar género");
        if (!generoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un género inexistente");
        }
        generoRepository.deleteById(id);
        log.info("Género eliminado con id: {}", id);
    }
}
