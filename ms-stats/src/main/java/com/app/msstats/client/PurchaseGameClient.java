package com.app.msstats.client;

import com.app.msstats.dto.external.PurchaseGameResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "purchase-game-client-stats", url = "http://localhost:8083/api/v1/purchases")
public interface PurchaseGameClient {

    @GetMapping("/game/{gameId}/stats")
    List<PurchaseGameResponse> getAllPurchasesByGameId(@PathVariable Long gameId);

    @GetMapping("/user/{userId}/stats")
    List<PurchaseGameResponse> findAllByUserId(@PathVariable Long userId);
}