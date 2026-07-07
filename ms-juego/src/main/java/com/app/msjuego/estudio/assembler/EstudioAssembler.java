package com.app.msjuego.estudio.assembler;

import com.app.msjuego.estudio.controller.EstudioController;
import com.app.msjuego.estudio.dto.EstudioResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstudioAssembler implements RepresentationModelAssembler<EstudioResponse, EntityModel<EstudioResponse>> {

    @Override
    @Nonnull
    public EntityModel<EstudioResponse> toModel(@Nonnull EstudioResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(EstudioController.class).findById(response.id())).withSelfRel()
        );
    }
}
