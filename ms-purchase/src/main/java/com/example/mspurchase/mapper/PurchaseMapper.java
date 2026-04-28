package com.example.mspurchase.mapper;

import com.example.mspurchase.dto.PurchaseRequest;
import com.example.mspurchase.dto.PurchaseResponse;
import com.example.mspurchase.dto.external.JuegoResponse;
import com.example.mspurchase.dto.external.UserResponse;
import com.example.mspurchase.model.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {
    public Purchase toEntity(PurchaseRequest purchaseRequest, JuegoResponse juegoResponse, UserResponse userResponse){
        return Purchase.builder()
                .juegoId(juegoResponse.id())
                .usuarioId(userResponse.id())
                .precio(juegoResponse.precio())
                .estado(purchaseRequest.estado())
                .build();
    }
    public PurchaseResponse toResponse(Purchase purchase, JuegoResponse juegoResponse, UserResponse userResponse){
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .juegoId(juegoResponse.id())
                .usuarioId(userResponse.id())
                .precio(juegoResponse.precio())
                .estado(purchase.getEstado())
                .fechaRegistro(purchase.getFechaRegistro())
                .nombreJuego(juegoResponse.nombre())
                .nombreUsuario(userResponse.nombre())
                .email(userResponse.email())
                .build();

    }
}
