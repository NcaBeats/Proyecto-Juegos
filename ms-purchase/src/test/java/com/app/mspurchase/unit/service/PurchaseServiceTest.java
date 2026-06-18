package com.app.mspurchase.unit.service;

import com.app.mspurchase.purchase.client.LibraryClient;
import com.app.mspurchase.purchase.client.NotificationClient;
import com.app.mspurchase.purchase.client.UserClient;
import com.app.mspurchase.purchase.dto.PurchaseResponse;
import com.app.mspurchase.purchase.mapper.PurchaseMapper;
import com.app.mspurchase.purchase.model.Purchase;
import com.app.mspurchase.purchase.repository.PurchaseRepository;
import com.app.mspurchase.purchase.service.PurchaseService;
import com.app.mspurchase.purchasegame.client.JuegoClient;
import com.app.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import com.app.mspurchase.purchasegame.model.PurchaseGame;
import com.app.mspurchase.purchasegame.repository.PurchaseGameRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.mspurchase.support.PurchaseFactory.*;
import static com.app.mspurchase.support.PurchaseGameFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PurchaseGameRepository purchaseGameRepository;
    @Mock
    private PurchaseMapper purchaseMapper;
    @Mock
    private JuegoClient juegoClient;
    @Mock
    private UserClient userClient;
    @Mock
    private NotificationClient notificationClient;
    @Mock
    private LibraryClient libraryClient;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void findAllByUserId_ReturnsPage() {
        Purchase purchase = createPurchaseEntity();
        Page<Purchase> page = new PageImpl<>(List.of(purchase));
        Pageable pageable = PageRequest.of(0, 10);

        when(purchaseRepository.findAllByUserId(USER_ID, pageable)).thenReturn(page);
        when(purchaseMapper.toResponse(purchase)).thenReturn(PURCHASE_RESPONSE);

        Page<PurchaseResponse> result = purchaseService.findAllByUserId(USER_ID, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(purchaseRepository).findAllByUserId(USER_ID, pageable);
    }

    @Test
    void findAll_ReturnsPage() {
        Purchase purchase = createPurchaseEntity();
        Page<Purchase> page = new PageImpl<>(List.of(purchase));
        Pageable pageable = PageRequest.of(0, 10);

        when(purchaseRepository.findAll(pageable)).thenReturn(page);
        when(purchaseMapper.toResponse(purchase)).thenReturn(PURCHASE_RESPONSE);

        Page<PurchaseResponse> result = purchaseService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(purchaseRepository).findAll(pageable);
    }

    @Test
    void createPurchase_Success() {
        Purchase purchase = createPurchaseEntity();

        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE_SUFICIENTE);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(libraryClient.gameExists(USER_ID, GAME_ID)).thenReturn(false);
        when(purchaseMapper.toEntity(PURCHASE_REQUEST)).thenReturn(purchase);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        when(purchaseMapper.toResponse(purchase)).thenReturn(PURCHASE_RESPONSE);

        PurchaseResponse result = purchaseService.createPurchase(PURCHASE_REQUEST);

        assertNotNull(result);
        verify(userClient).getUserById(USER_ID);
        verify(juegoClient).getJuegoById(GAME_ID);
        verify(libraryClient).gameExists(USER_ID, GAME_ID);
        verify(purchaseMapper).toEntity(PURCHASE_REQUEST);
        verify(purchaseRepository).save(any(Purchase.class));
        verify(libraryClient).addGamesToLibrary(eq(USER_ID), anyList());
        verify(notificationClient).createNotification(any());
        verify(userClient).updateBalance(USER_ID, PRECIO);
        verify(purchaseMapper).toResponse(purchase);
    }

    @Test
    void createPurchase_ThrowsEntityExistsException_WhenGameAlreadyOwned() {
        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE_SUFICIENTE);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(libraryClient.gameExists(USER_ID, GAME_ID)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> purchaseService.createPurchase(PURCHASE_REQUEST));

        verify(userClient).getUserById(USER_ID);
        verify(juegoClient).getJuegoById(GAME_ID);
        verify(libraryClient).gameExists(USER_ID, GAME_ID);
        verifyNoInteractions(purchaseRepository, notificationClient, purchaseMapper);
    }

    @Test
    void createPurchase_ThrowsIllegalStateException_WhenInsufficientBalance() {
        Purchase purchase = createPurchaseEntity();

        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE_INSUFICIENTE);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(libraryClient.gameExists(USER_ID, GAME_ID)).thenReturn(false);
        when(purchaseMapper.toEntity(PURCHASE_REQUEST)).thenReturn(purchase);

        assertThrows(IllegalStateException.class, () -> purchaseService.createPurchase(PURCHASE_REQUEST));

        verify(userClient).getUserById(USER_ID);
        verify(juegoClient).getJuegoById(GAME_ID);
        verify(libraryClient).gameExists(USER_ID, GAME_ID);
        verify(purchaseMapper).toEntity(PURCHASE_REQUEST);
        verify(purchaseRepository, never()).save(any());
        verifyNoInteractions(notificationClient);
    }

    @Test
    void createPurchase_Success_WhenNotificationFails() {
        Purchase purchase = createPurchaseEntity();

        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE_SUFICIENTE);
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);
        when(libraryClient.gameExists(USER_ID, GAME_ID)).thenReturn(false);
        when(purchaseMapper.toEntity(PURCHASE_REQUEST)).thenReturn(purchase);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        when(purchaseMapper.toResponse(purchase)).thenReturn(PURCHASE_RESPONSE);

        doThrow(new RuntimeException("Error de conexión")).when(notificationClient).createNotification(any());

        PurchaseResponse result = purchaseService.createPurchase(PURCHASE_REQUEST);

        assertNotNull(result);
        verify(userClient).getUserById(USER_ID);
        verify(purchaseRepository).save(any(Purchase.class));
        verify(notificationClient).createNotification(any());
        verify(userClient).updateBalance(USER_ID, PRECIO);
    }

    @Test
    void findAllByGameIdForStats_ReturnsList() {
        Purchase purchase = createPurchaseEntity();
        PurchaseGame purchaseGame = createPurchaseGameEntity();
        purchaseGame.setPurchase(purchase);

        when(purchaseGameRepository.findByGameId(GAME_ID)).thenReturn(List.of(purchaseGame));
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);

        List<PurchaseGameStatsResponse> result = purchaseService.findAllByGameIdForStats(GAME_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(PURCHASE_GAME_STATS_RESPONSE, result.getFirst());
        verify(purchaseGameRepository).findByGameId(GAME_ID);
        verify(juegoClient).getJuegoById(GAME_ID);
    }

    @Test
    void findAllByUserIdForStats_ReturnsList() {
        Purchase purchase = createPurchaseEntity();
        PurchaseGame purchaseGame = createPurchaseGameEntity();
        purchaseGame.setPurchase(purchase);

        when(purchaseGameRepository.findByPurchaseUserId(USER_ID)).thenReturn(List.of(purchaseGame));
        when(juegoClient.getJuegoById(GAME_ID)).thenReturn(JUEGO_RESPONSE);

        List<PurchaseGameStatsResponse> result = purchaseService.findAllByUserIdForStats(USER_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(PURCHASE_GAME_STATS_RESPONSE, result.getFirst());
        verify(purchaseGameRepository).findByPurchaseUserId(USER_ID);
        verify(juegoClient).getJuegoById(GAME_ID);
    }
}
