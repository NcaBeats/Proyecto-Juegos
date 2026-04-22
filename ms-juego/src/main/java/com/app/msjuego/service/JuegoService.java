package com.app.msjuego.service;

import com.app.msjuego.dto.JuegoResponse;
import com.app.msjuego.mapper.JuegoMapper;
import com.app.msjuego.model.Juego;
import com.app.msjuego.repository.JuegoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
}
