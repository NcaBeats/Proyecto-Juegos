package com.app.msstats.service;

import com.app.msstats.client.JuegoClient;
import com.app.msstats.client.ProfileClient;
import com.app.msstats.client.PurchaseGameClient;
import com.app.msstats.client.ReviewClient;
import com.app.msstats.dto.GameStatsResponse;
import com.app.msstats.dto.UserStatsResponse;
import com.app.msstats.dto.external.PurchaseGameResponse;
import com.app.msstats.dto.external.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {

    private final ReviewClient reviewClient;
    private final PurchaseGameClient purchaseGameClient;
    private final ProfileClient profileClient;
    private final JuegoClient juegoClient;

    public GameStatsResponse getGameStats(Long gameId) {
        log.info("Calculando stats para gameId={}", gameId);
        log.debug("Llamando a PurchaseGameClient.getAllPurchasesByGameId gameId={}", gameId);
        var purchases = purchaseGameClient.getAllPurchasesByGameId(gameId);
        log.debug("Llamando a ReviewClient.getReviewsByGameId gameId={}", gameId);
        var reviews = reviewClient.getReviewsByGameId(gameId);
        log.debug("Llamando a JuegoClient.getJuego gameId={}", gameId);
        var game = juegoClient.getJuego(gameId);

        int copiasVendidas = purchases.stream()
                .mapToInt(PurchaseGameResponse::cantidad)
                .sum();
        BigDecimal ventasTotales = game.precio()
                .multiply(BigDecimal.valueOf(copiasVendidas));

        log.info("Stats calculadas gameId={} copiasVendidas={} ventasTotales={}", gameId, copiasVendidas, ventasTotales);

        return GameStatsResponse.builder()
                .gameId(gameId)
                .gameName(game.nombre())
                .copiasVendidas(copiasVendidas)
                .ventasTotales(ventasTotales)
                .ratingPromedio(calculateAverageRating(reviews))
                .build();
    }
    public UserStatsResponse getUserStats(Long userId) {
        log.info("Calculando stats para userId={}", userId);
        log.debug("Llamando a PurchaseGameClient.findAllByUserId userId={}", userId);
        var purchases = purchaseGameClient.findAllByUserId(userId);
        log.debug("Llamando a ReviewClient.getReviewsByUserId userId={}", userId);
        var reviews = reviewClient.getReviewsByUserId(userId);
        log.debug("Llamando a ProfileClient.getProfileByUserId userId={}", userId);
        var profile = profileClient.getProfileByUserId(userId);

        BigDecimal dineroGastado = purchases.stream()
                .map(PurchaseGameResponse::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                // Él reduce aplica una operación muchas veces sobre una colección
                // y el add especifica esa operación
        log.info("Stats calculadas userId={} juegosComprados={} dineroGastado={}", userId, purchases.size(), dineroGastado);
        return UserStatsResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .avatar(profile.avatar())
                .juegosComprados(purchases.size())
                .dineroGastado(dineroGastado)
                .promedioRatingDado(calculateAverageRating(reviews))
                .build();
    }

    private double calculateAverageRating(List<ReviewResponse> reviews) {
        return (reviews.stream()
                .mapToInt(ReviewResponse::rating)
                .average()
                .orElse(0.0));
    }
}
