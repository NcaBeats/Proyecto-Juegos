package com.app.msusuario.service;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.mapper.UsuarioMapper;
import com.app.msusuario.model.Usuario;
import com.app.msusuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Page<UsuarioResponse> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toResponse);
    }
    public UsuarioResponse findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        return usuarioMapper.toResponse(usuario);
    }
    public UsuarioResponse findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el email " + email) );
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByNombre(String nombre) {
        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el nombre " + nombre) );
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse save (UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        Usuario userSaved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(userSaved);
    }
    @Transactional
    public UsuarioResponse update(Long id,UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        usuario.update(usuarioRequest);
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id " + id));
        usuarioRepository.delete(usuario);
    }
    @Transactional
    public void restarSaldo(Long userId, BigDecimal monto) {

        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (user.getSaldo().compareTo(monto) < 0) {
            // si el saldo es mayor que el monto, devuelve 1,
            // si es igual devuelve 0,
            // si es menor devuelve -1
            // se usa compareTo porque BigDecimal es un Objeto
            throw new RuntimeException("saldo insuficiente");
        }
        user.setSaldo(user.getSaldo().subtract(monto));
    }
}
