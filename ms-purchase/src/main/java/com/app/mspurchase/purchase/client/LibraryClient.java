package com.app.mspurchase.purchase.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-library")
public interface LibraryClient {

    @PostMapping("/api/v1/library/{userId}/games")
    void addGamesToLibrary(@PathVariable Long userId, @RequestBody List<Long> gameIds);
    @GetMapping("/api/v1/library/{userId}/game/{gameId}/exists")
    boolean gameExists(@PathVariable Long userId, @PathVariable Long gameId);
}