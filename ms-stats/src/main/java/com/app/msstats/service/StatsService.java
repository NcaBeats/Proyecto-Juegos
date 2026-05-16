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

import java.math.BigDecimal;
import java.util.List;


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

        int copiasVendidas = purchases.stream()
                .mapToInt(PurchaseGameResponse::cantidad)
                .sum();
        BigDecimal ventasTotales = game.precio()
                .multiply(BigDecimal.valueOf(copiasVendidas));

        return GameStatsResponse.builder()
                .gameId(gameId)
                .gameName(game.nombre())
                .copiasVendidas(copiasVendidas)
                .ventasTotales(ventasTotales)
                .ratingPromedio(calculateAverageRating(reviews))
                .build();
    }
    public UserStatsResponse getUserStats(Long userId) {
        var purchases = purchaseGameClient.findAllByUserId(userId);
        var reviews = reviewClient.getReviewsByUserId(userId);
        var profile = profileClient.getProfileByUserId(userId);

        BigDecimal dineroGastado = purchases.stream()
                .map(PurchaseGameResponse::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                // Él reduce aplica una operación muchas veces sobre una colección
                // y el add especifica esa operación
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