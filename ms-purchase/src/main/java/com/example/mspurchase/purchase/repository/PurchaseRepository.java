package com.example.mspurchase.purchase.repository;

import com.example.mspurchase.purchase.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
