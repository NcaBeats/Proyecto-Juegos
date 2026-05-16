package com.app.msjuego.juego.service;

import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.estudio.repository.EstudioRepository;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.genero.repository.GeneroRepository;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.mapper.JuegoMapper;
import com.app.msjuego.juego.model.Juego;
import com.app.msjuego.juego.repository.JuegoRepository;
import com.app.msjuego.plataforma.model.Plataforma;
import com.app.msjuego.plataforma.repository.PlataformaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JuegoService {
    private final JuegoRepository juegoRepository;
    private final JuegoMapper juegoMapper;
    private final EstudioRepository estudioRepository;
    private final GeneroRepository generoRepository;
    private final PlataformaRepository plataformaRepository;

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

    public Page<JuegoResponse> getAllByEstudioId(Long estudioId, Pageable pageable) {
        return juegoRepository.getAllByEstudioId(estudioId, pageable).map(juegoMapper::toResponse);
    }

    public Page<JuegoResponse> getAllByGenerosId(Long generoId, Pageable pageable) {
        return juegoRepository.getAllByGenerosId(generoId, pageable).map(juegoMapper::toResponse);
    }

    public Page<JuegoResponse> getAllByPlataformasId(Long plataformaId, Pageable pageable) {
        return juegoRepository.getAllByPlataformasId(plataformaId, pageable).map(juegoMapper::toResponse);
    }

    @Transactional
    public JuegoResponse save(JuegoRequest request) {
        Estudio estudio = estudioRepository.findById(request.estudioId())
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        List<Genero> generos = generoRepository.findAllById(request.generoIds());
        List<Plataforma> plataformas = plataformaRepository.findAllById(request.plataformaIds());

        Juego juego = juegoMapper.toEntity(request, estudio, generos, plataformas);
        return juegoMapper.toResponse(juegoRepository.save(juego));
    }

    @Transactional
    public JuegoResponse update(Long id, JuegoRequest request) {
        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado"));

        Estudio estudio = estudioRepository.findById(request.estudioId())
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        List<Genero> generos = generoRepository.findAllById(request.generoIds());
        List<Plataforma> plataformas = plataformaRepository.findAllById(request.plataformaIds());

        juego.update(request, estudio, generos, plataformas);
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
