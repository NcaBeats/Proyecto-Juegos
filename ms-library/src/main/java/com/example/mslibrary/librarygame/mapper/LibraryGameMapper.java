package com.example.mslibrary.librarygame.mapper;

import com.example.mslibrary.librarygame.dto.LibraryGameResponse;
import com.example.mslibrary.librarygame.dto.external.JuegoResponse;
import com.example.mslibrary.librarygame.model.LibraryGame;
import org.springframework.stereotype.Component;

@Component
public class LibraryGameMapper {
    public LibraryGameResponse toResponse (LibraryGame entity, JuegoResponse juegoResponse){
        return LibraryGameResponse.builder()
                .id(entity.getId())
                .gameId(entity.getGameId())
                .gameName(juegoResponse.nombre())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }
}
