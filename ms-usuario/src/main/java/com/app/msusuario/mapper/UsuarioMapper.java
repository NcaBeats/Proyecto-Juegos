package com.app.msusuario.mapper;

import com.app.msusuario.dto.UsuarioRequest;
import com.app.msusuario.dto.UsuarioResponse;
import com.app.msusuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity (UsuarioRequest request){
        return Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .build();
    }

    public UsuarioResponse toResponse (Usuario usuario){
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .fecha_registro(usuario.getFecha_registro())
                .build();
    }
}
