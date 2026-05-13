package com.example.mspurchase.purchase.service;

import com.example.mspurchase.purchase.client.LibraryClient;
import com.example.mspurchase.purchase.client.NotificationClient;
import com.example.mspurchase.purchase.client.UserClient;
import com.example.mspurchase.purchase.dto.PurchaseRequest;
import com.example.mspurchase.purchase.dto.PurchaseResponse;
import com.example.mspurchase.purchase.dto.external.PurchaseNotificationRequest;
import com.example.mspurchase.purchase.dto.external.UserResponse;
import com.example.mspurchase.purchase.dto.external.GamePurchaseResponse;
import com.example.mspurchase.purchase.dto.external.enums.TipoNotification;
import com.example.mspurchase.purchase.mapper.PurchaseMapper;
import com.example.mspurchase.purchase.model.Purchase;
import com.example.mspurchase.purchase.repository.PurchaseRepository;
import com.example.mspurchase.purchasegame.client.JuegoClient;
import com.example.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import com.example.mspurchase.purchasegame.dto.external.JuegoResponse;
import com.example.mspurchase.purchasegame.model.PurchaseGame;
import com.example.mspurchase.purchasegame.repository.PurchaseGameRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseGameRepository purchaseGameRepository;
    private final PurchaseMapper purchaseMapper;
    private final JuegoClient juegoClient;
    private final UserClient userClient;
    private final NotificationClient notificationClient;
    private final LibraryClient libraryClient;

    public Page<PurchaseResponse> findAllByUserId(Long userId, Pageable pageable) {
        return purchaseRepository.findAllByUserId(userId, pageable)
                .map(purchaseMapper::toDTO);
    }

    public Page<PurchaseResponse> findAll(Pageable pageable) {
        return purchaseRepository.findAll(pageable)
                .map(purchaseMapper::toDTO);
    }

    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest dto) {
        UserResponse user = userClient.getUserById(dto.userId());
        List<JuegoResponse> juegos = dto.juegos()
                .stream()
                .map(juego -> juegoClient.getJuegoById(juego.gameId()))
                .toList();


        juegos.stream()
                .filter(j -> libraryClient.gameExists(dto.userId(), j.id()))
                .findAny()
                .ifPresent(j -> {
                    throw new EntityExistsException("Ya tienes el juego: " + j.nombre());
                });

        List<GamePurchaseResponse> juegosNotification = juegos.stream()
                .map(j -> GamePurchaseResponse.builder().id(j.id()).name(j.nombre()).precio(j.precio()).build())
                .toList();



        Purchase purchase = purchaseMapper.toEntity(dto);

        List<PurchaseGame> juegosPG = juegos.stream()
                .map(juego -> PurchaseGame.builder()
                        .gameId(juego.id())
                        .purchase(purchase)
                        .build()).toList();

        purchase.setJuegos(juegosPG);

        BigDecimal total = juegos.stream().map(JuegoResponse::precio).reduce(BigDecimal.ZERO, BigDecimal::add);
        purchase.setTotalPrecio(total);

        if (user.saldo().compareTo(purchase.getTotalPrecio()) < 0) {
            throw new RuntimeException("saldo insuficiente");
        }

        Purchase saved = purchaseRepository.save(purchase);

        List<Long> gameIds = juegos.stream()
                .map(JuegoResponse::id)
                .toList();

        libraryClient.addGamesToLibrary(dto.userId(), gameIds);

        PurchaseNotificationRequest request = PurchaseNotificationRequest.builder()
                .userId(dto.userId())
                .message("Compra: " + juegosNotification.stream().map(GamePurchaseResponse::name).collect(Collectors.joining(", ")))
                .tipo(TipoNotification.COMPRA)
                .juegos(juegosNotification)
                .build();
        notificationClient.createNotification(request);
        userClient.updateBalance(user.id(), total);
        return purchaseMapper.toDTO(saved);
    }

    public List<PurchaseGameStatsResponse> findAllByGameIdForStats(Long gameId) {
        List<PurchaseGame> purchaseGames = purchaseGameRepository.findByGameId(gameId);
        return purchaseGames.stream()
                .map(pg -> {
                    JuegoResponse juego = juegoClient.getJuegoById(pg.getGameId());
                    return PurchaseGameStatsResponse.builder()
                            .userId(pg.getPurchase().getUserId())
                            .gameId(pg.getGameId())
                            .gameName(juego.nombre())
                            .cantidad(1)
                            .price(juego.precio())
                            .build();
                })
                .toList();
    }

    public List<PurchaseGameStatsResponse> findAllByUserIdForStats(Long userId) {
        List<PurchaseGame> purchaseGames = purchaseGameRepository.findByPurchaseUserId(userId);
        return purchaseGames.stream()
                .map(pg -> {
                    JuegoResponse juego = juegoClient.getJuegoById(pg.getGameId());
                    return PurchaseGameStatsResponse.builder()
                            .userId(pg.getPurchase().getUserId())
                            .gameId(pg.getGameId())
                            .gameName(juego.nombre())
                            .cantidad(1)
                            .price(juego.precio())
                            .build();
                })
                .toList();
    }
}
