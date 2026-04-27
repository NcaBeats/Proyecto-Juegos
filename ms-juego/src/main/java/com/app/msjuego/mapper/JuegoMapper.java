package com.app.msjuego.mapper;

import com.app.msjuego.dto.JuegoRequest;
import com.app.msjuego.dto.JuegoResponse;
import com.app.msjuego.model.Juego;
import org.springframework.stereotype.Component;

@Component
public class JuegoMapper {
    public Juego toEntity(JuegoRequest juegoRequest) {
        return Juego.builder()
                .nombre(juegoRequest.nombre())
                .descripcion(juegoRequest.descripcion())
                .precio(juegoRequest.precio())
                .build();
    }
    public JuegoResponse toResponse(Juego juego) {
        return JuegoResponse.builder()
                .id(juego.getId())
                .nombre(juego.getNombre())
                .descripcion(juego.getDescripcion())
                .precio(juego.getPrecio())
                .fecha_registro(juego.getFecha_registro())
                .build();
    }
}
