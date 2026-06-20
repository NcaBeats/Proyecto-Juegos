package com.app.msstats.client;

import com.app.msstats.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juego")
public interface JuegoClient {
    @GetMapping("/api/v1/juegos/{id}")
    JuegoResponse getJuego(@PathVariable Long id);
}
