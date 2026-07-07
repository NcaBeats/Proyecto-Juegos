package com.app.msjuego.genero.assembler;

import com.app.msjuego.genero.controller.GeneroController;
import com.app.msjuego.genero.dto.GeneroResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GeneroAssembler implements RepresentationModelAssembler<GeneroResponse, EntityModel<GeneroResponse>> {

    @Override
    @Nonnull
    public EntityModel<GeneroResponse> toModel(@Nonnull GeneroResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(GeneroController.class).findById(response.id())).withSelfRel()
        );
    }
}
