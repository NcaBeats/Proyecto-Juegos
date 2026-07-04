package com.app.msjuego.juego.assembler;

import com.app.msjuego.juego.controller.JuegoController;
import com.app.msjuego.juego.dto.JuegoResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JuegoAssembler implements RepresentationModelAssembler<JuegoResponse, EntityModel<JuegoResponse>> {

    @Override
    @Nonnull
    public EntityModel<JuegoResponse> toModel(@Nonnull JuegoResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(JuegoController.class).findById(response.id())).withSelfRel());
    }
}
