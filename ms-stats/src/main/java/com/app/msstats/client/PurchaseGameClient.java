package com.app.msstats.client;

import com.app.msstats.dto.external.PurchaseGameResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-purchase")
public interface PurchaseGameClient {

    @GetMapping("/api/v1/purchases/game/{gameId}/stats")
    List<PurchaseGameResponse> getAllPurchasesByGameId(@PathVariable Long gameId);

    @GetMapping("/api/v1/purchases/user/{userId}/stats")
    List<PurchaseGameResponse> findAllByUserId(@PathVariable Long userId);
}