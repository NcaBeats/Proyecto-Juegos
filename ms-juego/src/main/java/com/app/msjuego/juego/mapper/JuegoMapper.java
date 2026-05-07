package com.app.msjuego.juego.mapper;

import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.model.Juego;
import org.springframework.stereotype.Component;

@Component
public class JuegoMapper {
    public Juego toEntity(JuegoRequest juegoRequest) {
        return Juego.builder()
                .nombre(juegoRequest.nombre())
                .descripcion(juegoRequest.descripcion())
                .precio(juegoRequest.precio())
                .fechaLanzamiento(juegoRequest.fechaLanzamiento())
                .estado(juegoRequest.estado())
                .build();
    }
    public JuegoResponse toResponse(Juego juego) {
        return JuegoResponse.builder()
                .id(juego.getId())
                .nombre(juego.getNombre())
                .descripcion(juego.getDescripcion())
                .precio(juego.getPrecio())
                .fechaLanzamiento(juego.getFechaLanzamiento())
                .estado(juego.getEstado())
                .fecha_registro(juego.getFecha_registro())
                .build();
    }
}
