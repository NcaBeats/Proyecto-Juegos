package com.example.mspurchase.purchasegame.mapper;

import com.example.mspurchase.purchase.model.Purchase;
import com.example.mspurchase.purchasegame.dto.PurchaseFullResponse;
import com.example.mspurchase.purchasegame.dto.PurchaseGameResponse;
import com.example.mspurchase.purchasegame.dto.external.JuegoResponse;
import com.example.mspurchase.purchasegame.model.PurchaseGame;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseGameMapper {
    public PurchaseGame toEntity(Long gameId, Purchase purchase) {
        return PurchaseGame.builder().gameId(gameId).purchase(purchase).build();
    }

    public PurchaseGameResponse toResponse(PurchaseGame entity, JuegoResponse juego) {
        return PurchaseGameResponse.builder()
                .id(entity.getId()).gameId(entity.getGameId())
                .gameName(juego.nombre()).price(juego.precio()).build();
    }

    public PurchaseFullResponse toFullResponse(Purchase purchase, String nickname, List<PurchaseGameResponse> games) {
        return PurchaseFullResponse.builder()
                .purchaseId(purchase.getId()).nickname(nickname)
                .total(purchase.getTotalPrecio()).games(games)
                .fecha(purchase.getFechaCompra()).build();
    }
}
