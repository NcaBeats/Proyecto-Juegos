package com.app.mspurchase.purchasegame.mapper;

import com.app.mspurchase.purchasegame.dto.PurchaseGameRequest;
import com.app.mspurchase.purchasegame.dto.PurchaseGameResponse;
import com.app.mspurchase.purchasegame.model.PurchaseGame;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseGameMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchase", ignore = true)
    PurchaseGame toEntity(PurchaseGameRequest dto);

    PurchaseGameResponse toDTO(PurchaseGame entity);
}