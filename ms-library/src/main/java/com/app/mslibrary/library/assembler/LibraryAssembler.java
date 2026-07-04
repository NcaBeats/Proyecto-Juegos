package com.app.mslibrary.library.assembler;

import com.app.mslibrary.library.controller.LibraryController;
import com.app.mslibrary.library.dto.LibraryResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LibraryAssembler implements RepresentationModelAssembler<LibraryResponse, EntityModel<LibraryResponse>> {

    @Override
    @Nonnull
    public EntityModel<LibraryResponse> toModel(@Nonnull LibraryResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(LibraryController.class).getAllByUserId(response.userId())).withSelfRel());
    }
}
