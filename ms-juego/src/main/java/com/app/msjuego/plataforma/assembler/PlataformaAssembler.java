package com.app.msjuego.plataforma.assembler;

import com.app.msjuego.plataforma.controller.PlataformaController;
import com.app.msjuego.plataforma.dto.PlataformaResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlataformaAssembler implements RepresentationModelAssembler<PlataformaResponse, EntityModel<PlataformaResponse>> {

    @Override
    @Nonnull
    public EntityModel<PlataformaResponse> toModel(@Nonnull PlataformaResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(PlataformaController.class).findById(response.id())).withSelfRel()
        );
    }
}
