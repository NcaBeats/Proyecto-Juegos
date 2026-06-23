package com.app.mswishlist.wishlistgame.client;

import com.app.mswishlist.wishlistgame.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juego")
public interface JuegoClient {
    @GetMapping("/api/v1/juegos/{gameId}")
    JuegoResponse getJuegoById(@PathVariable Long gameId);
}
