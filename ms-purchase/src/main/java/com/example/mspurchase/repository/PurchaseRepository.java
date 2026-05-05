package com.example.mspurchase.repository;

import com.example.mspurchase.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findByUsuarioId(Long usuarioId, Pageable pageable);
}
