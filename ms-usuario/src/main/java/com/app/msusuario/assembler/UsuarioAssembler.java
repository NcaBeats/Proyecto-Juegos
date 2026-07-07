package com.app.msusuario.assembler;

import com.app.msusuario.controller.UsuarioController;
import com.app.msusuario.dto.UsuarioResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<UsuarioResponse, EntityModel<UsuarioResponse>> {

    @Override
    @Nonnull
    public EntityModel<UsuarioResponse> toModel(@Nonnull UsuarioResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(UsuarioController.class).findById(response.id())).withSelfRel()
        );
    }
}
