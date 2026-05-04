package com.example.mspurchase.service;

import com.example.mspurchase.dto.PurchaseRequest;
import com.example.mspurchase.dto.PurchaseResponse;
import com.example.mspurchase.dto.external.JuegoResponse;
import com.example.mspurchase.dto.external.UserResponse;
import com.example.mspurchase.mapper.PurchaseMapper;
import com.example.mspurchase.model.Purchase;
import com.example.mspurchase.repository.PurchaseRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    @Transactional
    public PurchaseResponse registrarCompra(PurchaseRequest request, JuegoResponse juego, UserResponse usuario) {
        Purchase nuevaCompra = purchaseMapper.toEntity(request, juego, usuario);
        Purchase compraGuardada = purchaseRepository.save(nuevaCompra);
        return purchaseMapper.toResponse(compraGuardada, juego, usuario);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> obtenerHistorial(Long usuarioId) {
        List<Purchase> comprasDB = purchaseRepository.findByUsuarioId(usuarioId);
        return comprasDB.stream()
                .map(p -> purchaseMapper.toResponse(p,
                        new JuegoResponse(p.getJuegoId(), "Cargando...", p.getPrecio()),
                        new UserResponse(p.getUsuarioId(), "Cargando...", "Cargando...")))
                .collect(Collectors.toList());
    }
}
