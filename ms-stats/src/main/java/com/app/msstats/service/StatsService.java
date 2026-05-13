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
import java.math.RoundingMode;
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
        return GameStatsResponse.builder()
                .gameId(gameId)
                .gameName(game.nombre())
                .ventasTotales(purchases.stream().mapToLong(PurchaseGameResponse::cantidad).sum())
                .ratingPromedio(calculateAverageRating(reviews))
                .build();
    }
    public UserStatsResponse getUserStats(Long userId) {
        var purchases = purchaseGameClient.findAllByUserId(userId);
        var reviews = reviewClient.getReviewsByUserId(userId);
        var profile = profileClient.getProfileByUserId(userId);
        return UserStatsResponse.builder()
                .userId(userId)
                .nickname(profile.nickname())
                .avatar(profile.avatar())
                .juegosComprados(purchases.size())
                .dineroGastado(purchases.stream().map(PurchaseGameResponse::price).reduce(BigDecimal.ZERO, BigDecimal::add))
                .promedioRatingDado(calculateAverageRating(reviews))
                .build();
    }

    private BigDecimal calculateAverageRating(List<ReviewResponse> reviews) {
        if (reviews.isEmpty()) return BigDecimal.ZERO;
        return BigDecimal.valueOf(reviews.stream()
                .mapToInt(ReviewResponse::rating)
                .average()
                .orElse(0.0))
                .setScale(1, RoundingMode.DOWN);
    }
}