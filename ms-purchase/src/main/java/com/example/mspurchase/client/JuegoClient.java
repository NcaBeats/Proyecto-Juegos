package com.example.mspurchase.client;

import com.example.mspurchase.dto.external.JuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juego", url = "http://localhost:8081/api/v1/juegos")
public interface JuegoClient {
    @GetMapping("/{id}")
    JuegoResponse getJuegoById(@PathVariable("id") Long id);
}