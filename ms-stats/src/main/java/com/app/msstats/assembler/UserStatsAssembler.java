package com.app.msstats.assembler;

import com.app.msstats.controller.StatsController;
import com.app.msstats.dto.UserStatsResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserStatsAssembler implements RepresentationModelAssembler<UserStatsResponse, EntityModel<UserStatsResponse>> {

    @Override
    @Nonnull
    public EntityModel<UserStatsResponse> toModel(@Nonnull UserStatsResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(StatsController.class).getStatsByUser(response.userId())).withSelfRel());
    }
}
