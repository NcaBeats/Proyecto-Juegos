package com.app.msjuego.plataforma.mapper;

import com.app.msjuego.plataforma.dto.PlataformaRequest;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import com.app.msjuego.plataforma.model.Plataforma;
import org.springframework.stereotype.Component;

@Component
public class PlataformaMapper {
    public Plataforma toEntity(PlataformaRequest request) {
        return Plataforma.builder()
                .nombre(request.nombre())
                .build();
    }

    public PlataformaResponse toResponse(Plataforma plataforma) {
        return PlataformaResponse.builder()
                .id(plataforma.getId())
                .nombre(plataforma.getNombre())
                .fechaCreacion(plataforma.getFechaCreacion())
                .build();
    }
}