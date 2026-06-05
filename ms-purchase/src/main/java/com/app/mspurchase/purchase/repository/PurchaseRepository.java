package com.app.mspurchase.purchase.repository;

import com.app.mspurchase.purchase.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findAllByUserId(Long userId, Pageable pageable);
}
