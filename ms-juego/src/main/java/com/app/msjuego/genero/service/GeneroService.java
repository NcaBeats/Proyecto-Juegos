package com.app.msjuego.genero.service;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.mapper.GeneroMapper;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.genero.repository.GeneroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneroService {
    private final GeneroRepository generoRepository;
    private final GeneroMapper generoMapper;

    public GeneroResponse findById(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado con id: " + id));
        return generoMapper.toResponse(genero);
    }

    public Page<GeneroResponse> findAll(Pageable pageable) {
        return generoRepository.findAll(pageable).map(generoMapper::toResponse);
    }

    @Transactional
    public GeneroResponse save(GeneroRequest request) {
        Genero genero = generoMapper.toEntity(request);
        return generoMapper.toResponse(generoRepository.save(genero));
    }

    @Transactional
    public GeneroResponse update(Long id, GeneroRequest request) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado"));
        genero.update(request);
        return generoMapper.toResponse(generoRepository.save(genero));
    }

    @Transactional
    public void delete(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un genero inexistente");
        }
        generoRepository.deleteById(id);
    }
}