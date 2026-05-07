package com.app.msstats.client;

import com.app.msstats.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "juego-service.stats", url = "http://localhost:8080/api/v1/juegos")
public interface JuegoClient {
    @GetMapping("/{id}")
    JuegoResponse getJuego(@PathVariable Long id);
}
