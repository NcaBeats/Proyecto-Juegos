package com.app.msstats.unit.service;

import com.app.msstats.client.JuegoClient;
import com.app.msstats.client.ProfileClient;
import com.app.msstats.client.PurchaseGameClient;
import com.app.msstats.client.ReviewClient;
import com.app.msstats.dto.external.PurchaseGameResponse;
import com.app.msstats.dto.external.ReviewResponse;
import com.app.msstats.service.StatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.app.msstats.support.StatsFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    ReviewClient reviewClient;
    @Mock
    PurchaseGameClient purchaseGameClient;
    @Mock
    ProfileClient profileClient;
    @Mock
    JuegoClient juegoClient;

    @InjectMocks
    StatsService statsService;

    @Test
    void getGameStats_ReturnsGameStatsResponse() {
        PurchaseGameResponse purchase1 = PurchaseGameResponse.builder()
                .userId(USER_ID).gameId(GAME_ID).gameName(GAME_NAME).cantidad(3).price(PRECIO)
                .build();
        PurchaseGameResponse purchase2 = PurchaseGameResponse.builder()
                .userId(USER_ID).gameId(GAME_ID).gameName(GAME_NAME).cantidad(2).price(PRECIO)
                .build();
        ReviewResponse review1 = ReviewResponse.builder().id(1L).userId(USER_ID).rating(4).build();
        ReviewResponse review2 = ReviewResponse.builder().id(2L).userId(USER_ID).rating(5).build();

        when(purchaseGameClient.getAllPurchasesByGameId(GAME_ID)).thenReturn(List.of(purchase1, purchase2));
        when(reviewClient.getReviewsByGameId(GAME_ID)).thenReturn(List.of(review1, review2));
        when(juegoClient.getJuego(GAME_ID)).thenReturn(JUEGO_RESPONSE);

        var result = statsService.getGameStats(GAME_ID);

        assertNotNull(result);
        assertEquals(GAME_STATS_RESPONSE, result);
        verify(purchaseGameClient).getAllPurchasesByGameId(GAME_ID);
        verify(reviewClient).getReviewsByGameId(GAME_ID);
        verify(juegoClient).getJuego(GAME_ID);
    }

    @Test
    void getGameStats_WhenNoData_ReturnsZeroValues() {
        when(purchaseGameClient.getAllPurchasesByGameId(GAME_ID)).thenReturn(List.of());
        when(reviewClient.getReviewsByGameId(GAME_ID)).thenReturn(List.of());
        when(juegoClient.getJuego(GAME_ID)).thenReturn(JUEGO_RESPONSE);

        var result = statsService.getGameStats(GAME_ID);

        assertNotNull(result);
        assertEquals(GAME_NAME, result.gameName());
        assertEquals(0, result.copiasVendidas());
        assertEquals(0, BigDecimal.ZERO.compareTo(result.ventasTotales()));
        assertEquals(0.0, result.ratingPromedio());
    }

    @Test
    void getGameStats_WithMultiplePurchases_SumsCantidadCorrectly() {
        PurchaseGameResponse p1 = createPurchaseGameResponseFaker();
        PurchaseGameResponse p2 = createPurchaseGameResponseFaker();
        int expectedCopias = p1.cantidad() + p2.cantidad();

        when(purchaseGameClient.getAllPurchasesByGameId(GAME_ID)).thenReturn(List.of(p1, p2));
        when(reviewClient.getReviewsByGameId(GAME_ID)).thenReturn(List.of());
        when(juegoClient.getJuego(GAME_ID)).thenReturn(JUEGO_RESPONSE);

        var result = statsService.getGameStats(GAME_ID);

        assertEquals(expectedCopias, result.copiasVendidas());
    }

    @Test
    void getUserStats_ReturnsUserStatsResponse() {
        PurchaseGameResponse purchase1 = createPurchaseGameResponse();
        PurchaseGameResponse purchase2 = createPurchaseGameResponse();
        ReviewResponse review1 = ReviewResponse.builder().id(1L).userId(USER_ID).rating(3).build();
        ReviewResponse review2 = ReviewResponse.builder().id(2L).userId(USER_ID).rating(4).build();

        when(purchaseGameClient.findAllByUserId(USER_ID)).thenReturn(List.of(purchase1, purchase2));
        when(reviewClient.getReviewsByUserId(USER_ID)).thenReturn(List.of(review1, review2));
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(PROFILE_RESPONSE);

        var result = statsService.getUserStats(USER_ID);

        assertNotNull(result);
        assertEquals(USER_STATS_RESPONSE, result);
        verify(purchaseGameClient).findAllByUserId(USER_ID);
        verify(reviewClient).getReviewsByUserId(USER_ID);
        verify(profileClient).getProfileByUserId(USER_ID);
    }

    @Test
    void getUserStats_WhenNoData_ReturnsZeroValues() {
        when(purchaseGameClient.findAllByUserId(USER_ID)).thenReturn(List.of());
        when(reviewClient.getReviewsByUserId(USER_ID)).thenReturn(List.of());
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(PROFILE_RESPONSE);

        var result = statsService.getUserStats(USER_ID);

        assertNotNull(result);
        assertEquals(NICKNAME, result.nickname());
        assertEquals(0, result.juegosComprados());
        assertEquals(0, BigDecimal.ZERO.compareTo(result.dineroGastado()));
        assertEquals(0.0, result.promedioRatingDado());
    }

    @Test
    void getUserStats_WithMultiplePurchases_SumsPriceCorrectly() {
        PurchaseGameResponse p1 = createPurchaseGameResponseFaker();
        PurchaseGameResponse p2 = createPurchaseGameResponseFaker();
        BigDecimal expectedGasto = p1.price().add(p2.price());

        when(purchaseGameClient.findAllByUserId(USER_ID)).thenReturn(List.of(p1, p2));
        when(reviewClient.getReviewsByUserId(USER_ID)).thenReturn(List.of());
        when(profileClient.getProfileByUserId(USER_ID)).thenReturn(PROFILE_RESPONSE);

        var result = statsService.getUserStats(USER_ID);

        assertEquals(0, expectedGasto.compareTo(result.dineroGastado()));
        assertEquals(2, result.juegosComprados());
    }
}
