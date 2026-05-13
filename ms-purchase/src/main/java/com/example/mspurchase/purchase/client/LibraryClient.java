package com.example.mspurchase.purchase.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "library-service" ,url = "http://localhost:8088/api/v1/library")
public interface LibraryClient {

    @PostMapping("/{userId}/games")
    void addGamesToLibrary(@PathVariable Long userId, @RequestBody List<Long> gameIds);
    @GetMapping("/{userId}/game/{gameId}/exists")
    boolean gameExists(@PathVariable Long userId, @PathVariable Long gameId);
}