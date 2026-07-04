package com.app.msprofile.assembler;

import com.app.msprofile.controller.ProfileController;
import com.app.msprofile.dto.ProfileResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileAssembler implements RepresentationModelAssembler<ProfileResponse, EntityModel<ProfileResponse>> {

    @Override
    @Nonnull
    public EntityModel<ProfileResponse> toModel(@Nonnull ProfileResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(ProfileController.class).findById(response.userId())).withSelfRel());
    }
}
