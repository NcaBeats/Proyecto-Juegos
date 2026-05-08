package com.app.msjuego.genero.mapper;

import com.app.msjuego.genero.dto.GeneroRequest;
import com.app.msjuego.genero.dto.GeneroResponse;
import com.app.msjuego.genero.model.Genero;
import org.springframework.stereotype.Component;

@Component
public class GeneroMapper {
    public Genero toEntity(GeneroRequest request) {
        return Genero.builder()
                .nombre(request.nombre())
                .build();
    }

    public GeneroResponse toResponse(Genero genero) {
        return GeneroResponse.builder()
                .id(genero.getId())
                .nombre(genero.getNombre())
                .fechaCreacion(genero.getFechaCreacion())
                .build();
    }
}