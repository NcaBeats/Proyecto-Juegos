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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class JuegoService {
    private final JuegoRepository juegoRepository;
    private final JuegoMapper juegoMapper;
    private final EstudioRepository estudioRepository;
    private final GeneroRepository generoRepository;
    private final PlataformaRepository plataformaRepository;

    public JuegoResponse findById (Long id) {
        log.debug("Buscando juego con id: {}", id);
        Juego juego = juegoRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Juego no encontrado con id: " + id));
        log.info("Juego con id {} encontrado", id);
        return juegoMapper.toResponse(juego);
    }

    public Page<JuegoResponse> findAll(Pageable pageable) {
        log.debug("Buscando página {} de juegos (Tamaño de página: {})", pageable.getPageNumber(), pageable.getPageSize());
        return juegoRepository.findAll(pageable).map(juegoMapper::toResponse);
    }

    public JuegoResponse findByNombre(String nombre) {
        log.debug("Buscando juego por nombre: {}", nombre);
        JuegoResponse resp = juegoRepository.findByNombre(nombre)
                .map(juegoMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado con el nombre: " + nombre));
        log.info("Juego encontrado por nombre: {}", nombre);
        return resp;
    }

    public Page<JuegoResponse> getAllByEstudioId(Long estudioId, Pageable pageable) {
        log.debug("Buscando juegos del estudio id: {} - página: {} tamaño: {}", estudioId, pageable.getPageNumber(), pageable.getPageSize());
        return juegoRepository.getAllByEstudioId(estudioId, pageable).map(juegoMapper::toResponse);
    }

    public Page<JuegoResponse> getAllByGenerosId(Long generoId, Pageable pageable) {
        log.debug("Buscando juegos por genero id: {} - página: {} tamaño: {}", generoId, pageable.getPageNumber(), pageable.getPageSize());
        return juegoRepository.getAllByGenerosId(generoId, pageable).map(juegoMapper::toResponse);
    }

    public Page<JuegoResponse> getAllByPlataformasId(Long plataformaId, Pageable pageable) {
        log.debug("Buscando juegos por plataforma id: {} - página: {} tamaño: {}", plataformaId, pageable.getPageNumber(), pageable.getPageSize());
        return juegoRepository.getAllByPlataformasId(plataformaId, pageable).map(juegoMapper::toResponse);
    }

    @Transactional
    public JuegoResponse save(JuegoRequest request) {
        log.info("Recibiendo petición para crear juego");
        Estudio estudio = estudioRepository.findById(request.estudioId())
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        log.debug("Obteniendo generos con ids: {}", request.generoIds());
        List<Genero> generos = generoRepository.findAllById(request.generoIds());
        log.debug("Generos obtenidos: {}", generos.size());
        log.debug("Obteniendo plataformas con ids: {}", request.plataformaIds());
        List<Plataforma> plataformas = plataformaRepository.findAllById(request.plataformaIds());
        log.debug("Plataformas obtenidas: {}", plataformas.size());

        Juego juego = juegoMapper.toEntity(request, estudio, generos, plataformas);
        Juego saved = juegoRepository.save(juego);
        log.info("Juego creado exitosamente con id: {}", saved.getId());
        return juegoMapper.toResponse(saved);
    }

    @Transactional
    public JuegoResponse update(Long id, JuegoRequest request) {
        log.info("Recibiendo petición para actualizar juego");
        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado"));

        Estudio estudio = estudioRepository.findById(request.estudioId())
                .orElseThrow(() -> new EntityNotFoundException("Estudio no encontrado"));
        log.debug("Obteniendo generos con ids: {}", request.generoIds());
        List<Genero> generos = generoRepository.findAllById(request.generoIds());
        log.debug("Generos obtenidos: {}", generos.size());
        log.debug("Obteniendo plataformas con ids: {}", request.plataformaIds());
        List<Plataforma> plataformas = plataformaRepository.findAllById(request.plataformaIds());
        log.debug("Plataformas obtenidas: {}", plataformas.size());

        juego.update(request, estudio, generos, plataformas);
        Juego updated = juegoRepository.save(juego);
        log.info("Juego con id: {} actualizado", updated.getId());
        return juegoMapper.toResponse(updated);
    }
    @Transactional
    public void delete(Long id) {
        log.info("Recibiendo petición para eliminar juego");
        if (!juegoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar un juego inexistente");
        }
        juegoRepository.deleteById(id);
        log.info("Juego eliminado con id: {}", id);
    }
}
