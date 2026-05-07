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
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StatsService {
    private final ReviewClient reviewClient;
    private final PurchaseGameClient purchaseGameClient;
    private final ProfileClient profileClient;
    private final JuegoClient juegoClient;
    public GameStatsResponse getGameStats(Long gameId) {

        var purchases = purchaseGameClient.getAllPurchasesByGameId(gameId);
        var reviews = reviewClient.getReviewsByGameId(gameId);
        var game = juegoClient.getJuego(gameId);
        long ventasTotales = purchases.stream()
                .mapToLong(PurchaseGameResponse::cantidad)
                .sum();

        double ratingPromedio = reviews.stream()
                .mapToInt(ReviewResponse::rating)
                .average()
                .orElse(0.0);

        return GameStatsResponse.builder()
                .gameId(gameId)
                .gameName(game.name())
                .ventasTotales(ventasTotales)
                .ratingPromedio(ratingPromedio)
                .build();
    }

    public UserStatsResponse getUserStats(Long userId) {
        var purchases = purchaseGameClient.getAllPurchasesByUserId(userId);
        var reviews = reviewClient.getReviewsByUserId(userId);
        var profile = profileClient.getProfileByUserId(userId);

        long juegosComprados = purchases.size(); // aquí se optó por contar los juegos totales incluso si se compra el mismo juego varias veces.
        double dineroGastado = purchases.stream()
                .mapToDouble(PurchaseGameResponse::price)
                .sum();
        double promedioRatingDado = reviews.stream()
                .mapToInt(ReviewResponse::rating)
                .average()
                .orElse(0.0);
        return UserStatsResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .avatar(profile.avatar())
                .juegosComprados(juegosComprados)
                .dineroGastado(dineroGastado)
                .promedioRatingDado(promedioRatingDado)
                .build();
    }
}
