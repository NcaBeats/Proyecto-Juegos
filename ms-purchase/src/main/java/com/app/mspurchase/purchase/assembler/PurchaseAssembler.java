package com.app.mspurchase.purchase.assembler;

import com.app.mspurchase.purchase.controller.PurchaseController;
import com.app.mspurchase.purchase.dto.PurchaseResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseAssembler implements RepresentationModelAssembler<PurchaseResponse, EntityModel<PurchaseResponse>> {

    @Override
    @Nonnull
    public EntityModel<PurchaseResponse> toModel(@Nonnull PurchaseResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(PurchaseController.class).findAllByUserId(response.userId(), null)).withRel("user-purchases"));
    }
}
