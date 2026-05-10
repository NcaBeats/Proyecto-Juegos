package com.example.mspurchase.purchase.service;

import com.example.mspurchase.purchase.model.Purchase;
import com.example.mspurchase.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase createHeader(Long userId) {
        return purchaseRepository.save(Purchase.builder()
                .userId(userId)
                .totalPrecio(BigDecimal.ZERO)
                .build());
    }
}
