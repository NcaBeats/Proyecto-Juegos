package com.app.mswishlist.wishlist.assembler;

import com.app.mswishlist.wishlist.controller.WishlistController;
import com.app.mswishlist.wishlist.dto.WishListResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WishlistAssembler implements RepresentationModelAssembler<WishListResponse, EntityModel<WishListResponse>> {

    @Override
    @Nonnull
    public EntityModel<WishListResponse> toModel(@Nonnull WishListResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(WishlistController.class).getAllByUserId(response.userId())).withSelfRel());
    }
}
