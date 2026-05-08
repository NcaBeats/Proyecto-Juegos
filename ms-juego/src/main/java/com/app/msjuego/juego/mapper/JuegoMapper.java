package com.app.msjuego.juego.mapper;

import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.model.Juego;
import com.app.msjuego.plataforma.model.Plataforma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JuegoMapper {

    @Mapping(target = "estudio", source = "estudio")
    @Mapping(target = "generos", source = "generos")
    @Mapping(target = "plataformas", source = "plataformas")
    JuegoResponse toResponse(Juego juego);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha_registro", ignore = true)
    @Mapping(target = "nombre", source = "request.nombre")
    @Mapping(target = "descripcion", source = "request.descripcion")
    @Mapping(target = "precio", source = "request.precio")
    @Mapping(target = "fechaLanzamiento", source = "request.fechaLanzamiento")
    @Mapping(target = "estado", source = "request.estado")
    @Mapping(target = "estudio", source = "estudio")
    @Mapping(target = "generos", source = "generos")
    @Mapping(target = "plataformas", source = "plataformas")
    Juego toEntity(JuegoRequest request, Estudio estudio, List<Genero> generos, List<Plataforma> plataformas);
}