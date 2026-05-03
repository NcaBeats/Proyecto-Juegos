package com.app.mswishlist.client;

import com.app.mswishlist.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ms-juego", url = "http://localhost:8080/api/v1/juegos")
public interface JuegoClient {
    @GetMapping("/{gameId}")
    JuegoResponse getJuegoById(Long gameId);
}
