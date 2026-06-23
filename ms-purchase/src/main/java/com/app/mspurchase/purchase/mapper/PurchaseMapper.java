package com.app.mspurchase.purchase.mapper;
import com.app.mspurchase.purchase.dto.PurchaseRequest;
import com.app.mspurchase.purchase.dto.PurchaseResponse;
import com.app.mspurchase.purchase.model.Purchase;
import com.app.mspurchase.purchasegame.mapper.PurchaseGameMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PurchaseGameMapper.class)
public interface PurchaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrecio", ignore = true)
    @Mapping(target = "fechaCompra", ignore = true)
    Purchase toEntity(PurchaseRequest dto);


    PurchaseResponse toResponse(Purchase purchase);
}
