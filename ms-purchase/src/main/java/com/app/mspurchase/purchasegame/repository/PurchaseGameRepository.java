package com.app.mspurchase.purchasegame.repository;

import com.app.mspurchase.purchasegame.model.PurchaseGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseGameRepository extends JpaRepository<PurchaseGame, Long> {
    List<PurchaseGame> findByGameId(Long gameId);
    List<PurchaseGame> findByPurchaseUserId(@Param("userId") Long userId);
}
