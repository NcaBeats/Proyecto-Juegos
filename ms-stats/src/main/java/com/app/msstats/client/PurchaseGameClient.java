package com.app.msstats.client;

import com.app.msstats.dto.external.PurchaseGameResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "purchase-game-client-stats", url = "${client.purchase.url}")//modificar
public interface PurchaseGameClient {
    @GetMapping("/purchases/game/{gameId}")//modificar
    List<PurchaseGameResponse> getAllPurchasesByGameId(Long gameId);

    @GetMapping("purchases/user/{userId}")
    List<PurchaseGameResponse> getAllPurchasesByUserId(Long userId);
}
