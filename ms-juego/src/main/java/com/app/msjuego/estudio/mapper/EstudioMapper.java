package com.app.msjuego.estudio.mapper;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.estudio.dto.EstudioResponse;
import com.app.msjuego.estudio.model.Estudio;
import org.springframework.stereotype.Component;

@Component
public class EstudioMapper {
    public Estudio toEntity(EstudioRequest request) {
        return Estudio.builder()
                .nombre(request.nombre())
                .build();
    }

    public EstudioResponse toResponse(Estudio estudio) {
        return EstudioResponse.builder()
                .id(estudio.getId())
                .nombre(estudio.getNombre())
                .fechaCreacion(estudio.getFechaCreacion())
                .build();
    }
}