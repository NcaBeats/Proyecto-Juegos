package com.example.mspurchase.service;

import com.example.mspurchase.client.JuegoClient;
import com.example.mspurchase.client.UserClient;
import com.example.mspurchase.dto.PurchaseRequest;
import com.example.mspurchase.dto.PurchaseResponse;
import com.example.mspurchase.dto.external.JuegoResponse;
import com.example.mspurchase.dto.external.UserResponse;
import com.example.mspurchase.mapper.PurchaseMapper;
import com.example.mspurchase.model.Purchase;
import com.example.mspurchase.repository.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final JuegoClient juegoClient;
    private final UserClient userClient;

    @Transactional
    public PurchaseResponse registrarCompra(PurchaseRequest request) {
        JuegoResponse juego = juegoClient.getJuegoById(request.juegoId());
        UserResponse usuario = userClient.getUsuarioById(request.usuarioId());
        Purchase nuevaCompra = purchaseMapper.toEntity(request, juego, usuario);
        return purchaseMapper.toResponse(purchaseRepository.save(nuevaCompra), juego, usuario);
    }

    @Transactional(readOnly = true)
    public Page<PurchaseResponse> obtenerHistorialPaginado(Long usuarioId, Pageable pageable) {
        Page<Purchase> comprasPage = purchaseRepository.findByUsuarioId(usuarioId, pageable);
        return comprasPage.map(p -> {
            JuegoResponse j = juegoClient.getJuegoById(p.getJuegoId());
            UserResponse u = userClient.getUsuarioById(p.getUsuarioId());
            return purchaseMapper.toResponse(p, j, u);
        });
    }
}
