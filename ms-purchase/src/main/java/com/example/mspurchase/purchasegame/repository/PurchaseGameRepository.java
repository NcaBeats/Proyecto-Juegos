package com.example.mspurchase.purchasegame.repository;

import com.example.mspurchase.purchasegame.model.PurchaseGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseGameRepository extends JpaRepository<PurchaseGame, Long> {
    Page<PurchaseGame> findByPurchaseId(Long purchaseId, Pageable pageable);
    Page<PurchaseGame> findByGameId(Long gameId, Pageable pageable);
}
