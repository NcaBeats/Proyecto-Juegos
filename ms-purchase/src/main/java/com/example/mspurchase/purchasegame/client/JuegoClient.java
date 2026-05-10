package com.example.mspurchase.purchasegame.client;

import com.example.mspurchase.purchasegame.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juego", url = "http://localhost:8080/api/v1/juegos")
public interface JuegoClient {
    @GetMapping("/{gameId}")
    JuegoResponse getJuegoById(@PathVariable Long gameId);
}
