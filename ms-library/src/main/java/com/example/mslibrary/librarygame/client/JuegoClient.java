package com.example.mslibrary.librarygame.client;

import com.example.mslibrary.librarygame.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juego-library",url = "http://localhost:8080/api/v1/juegos")
public interface JuegoClient {
    @GetMapping("/{id}")
    JuegoResponse findById (@PathVariable Long id);
}
