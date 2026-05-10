package com.example.mspurchase.purchasegame.service;

import com.example.mspurchase.purchase.model.Purchase;
import com.example.mspurchase.purchase.service.PurchaseService;
import com.example.mspurchase.purchasegame.client.JuegoClient;
import com.example.mspurchase.purchasegame.client.ProfileClient;
import com.example.mspurchase.purchasegame.dto.PurchaseFullResponse;
import com.example.mspurchase.purchasegame.dto.PurchaseGameResponse;
import com.example.mspurchase.purchasegame.dto.external.JuegoResponse;
import com.example.mspurchase.purchasegame.dto.external.ProfileResponse;
import com.example.mspurchase.purchasegame.mapper.PurchaseGameMapper;
import com.example.mspurchase.purchasegame.model.PurchaseGame;
import com.example.mspurchase.purchasegame.repository.PurchaseGameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseGameService {
    private final PurchaseService purchaseService;
    private final PurchaseGameRepository pgRepository;
    private final PurchaseGameMapper mapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;

    @Transactional
    public PurchaseFullResponse processPurchase(Long userId, List<Long> gameIds) {
        ProfileResponse profile = profileClient.getProfileByUserId(userId);
        for (Long gId : gameIds) {
            if (pgRepository.existsByPurchaseUserIdAndGameId(userId, gId)) {
                throw new IllegalStateException("El usuario ya posee el juego con ID: " + gId);
            }
        }
        Purchase header = purchaseService.createHeader(userId);
        List<PurchaseGameResponse> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Long gId : gameIds) {
            JuegoResponse juego = juegoClient.getJuegoById(gId);
            PurchaseGame pg = pgRepository.save(mapper.toEntity(gId, header));

            total = total.add(juego.precio());
            items.add(mapper.toResponse(pg, juego));
        }
        header.setTotalPrecio(total);
        return mapper.toFullResponse(header, profile.nickname(), items);
    }
}