package com.app.msjuego.service;

import com.app.msjuego.dto.JuegoRequest;
import com.app.msjuego.dto.JuegoResponse;
import com.app.msjuego.mapper.JuegoMapper;
import com.app.msjuego.model.Juego;
import com.app.msjuego.repository.JuegoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JuegoService {
    private final JuegoRepository juegoRepository;
    private final JuegoMapper juegoMapper;

    public JuegoResponse findById (Long id) {
        Juego juego = juegoRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Juego no encontrado con id: " + id));
        return juegoMapper.toResponse(juego);
    }

    public Page<JuegoResponse> findAll(Pageable pageable) {
        return juegoRepository.findAll(pageable).map(juegoMapper::toResponse);
    }

    public JuegoResponse findByNombre(String nombre) {
        return juegoRepository.findByNombre(nombre)
                .map(juegoMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado con el nombre: " + nombre));
    }

    @Transactional
    public JuegoResponse save(JuegoRequest request) {
        Juego juego = juegoMapper.toEntity(request);
        return juegoMapper.toResponse(juegoRepository.save(juego));
    }
    @Transactional
    public JuegoResponse update(Long id, JuegoRequest request) {
        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado"));
        juego.update(request);
        return juegoMapper.toResponse(juegoRepository.save(juego));
    }
    @Transactional
    public void delete(Long id) {
        if (!juegoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un juego inexistente");
        }
        juegoRepository.deleteById(id);
    }
}
