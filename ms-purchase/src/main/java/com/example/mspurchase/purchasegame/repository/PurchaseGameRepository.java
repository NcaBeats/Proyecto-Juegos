package com.example.mspurchase.purchasegame.repository;

import com.example.mspurchase.purchasegame.model.PurchaseGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseGameRepository extends JpaRepository<PurchaseGame, Long> {
    List<PurchaseGame> findByGameId(Long gameId);
    @Query("SELECT pg FROM PurchaseGame pg JOIN FETCH pg.purchase p WHERE p.userId = :userId")
    List<PurchaseGame> findByPurchaseUserId(@Param("userId") Long userId);
}
