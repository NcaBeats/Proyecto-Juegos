package com.app.msusuario.service;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.mapper.UsuarioMapper;
import com.app.msusuario.model.Usuario;
import com.app.msusuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Page<UsuarioResponse> findAll(Pageable pageable) {
        log.debug("Buscando usuarios - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toResponse);
    }
    public UsuarioResponse findById(Long id) {
        log.debug("Buscando usuario por id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        log.debug("Usuario encontrado id={}", id);
        return usuarioMapper.toResponse(usuario);
    }
    public UsuarioResponse findByEmail(String email) {
        log.debug("Buscando usuario por email: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el email " + email) );
        log.debug("Usuario encontrado email={}", email);
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByNombre(String nombre) {
        log.debug("Buscando usuario por nombre: {}", nombre);
        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el nombre " + nombre) );
        log.debug("Usuario encontrado nombre={}", nombre);
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse save (UsuarioRequest usuarioRequest) {
        log.info("Creando usuario email={}", usuarioRequest.email());
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        Usuario userSaved = usuarioRepository.save(usuario);
        log.info("Usuario creado id={} email={}", userSaved.getId(), userSaved.getEmail());
        return usuarioMapper.toResponse(userSaved);
    }
    @Transactional
    public UsuarioResponse update(Long id,UsuarioRequest usuarioRequest) {
        log.info("Actualizando usuario id={}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        usuario.update(usuarioRequest);
        log.info("Usuario actualizado id={}", id);
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando usuario id={}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        usuarioRepository.delete(usuario);
        log.info("Usuario eliminado id={}", id);
    }
    @Transactional
    public void restarSaldo(Long userId, BigDecimal monto) {
        log.info("Restando saldo userId={} monto={}", userId, monto);
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (user.getSaldo().compareTo(monto) < 0) {
            // si el saldo es mayor que el monto, devuelve 1,
            // si es igual devuelve 0,
            // si es menor devuelve -1
            // se usa compareTo porque BigDecimal es un Objeto
            throw new IllegalArgumentException("saldo insuficiente");
        }
        user.setSaldo(user.getSaldo().subtract(monto));
        log.info("Saldo actualizado userId={}", userId);
    }
}
