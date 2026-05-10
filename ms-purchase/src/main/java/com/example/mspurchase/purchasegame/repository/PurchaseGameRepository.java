package com.example.mspurchase.purchasegame.repository;

import com.example.mspurchase.purchasegame.model.PurchaseGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseGameRepository extends JpaRepository<PurchaseGame, Long> {
    List<PurchaseGame> findAllByPurchaseId(Long purchaseId);
    boolean existsByPurchaseUserIdAndGameId(Long userId, Long gameId);
}
