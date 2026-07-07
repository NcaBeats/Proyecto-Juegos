package com.app.msstats.assembler;

import com.app.msstats.controller.StatsController;
import com.app.msstats.dto.GameStatsResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameStatsAssembler implements RepresentationModelAssembler<GameStatsResponse, EntityModel<GameStatsResponse>> {

    @Override
    @Nonnull
    public EntityModel<GameStatsResponse> toModel(@Nonnull GameStatsResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(StatsController.class).getStatsByGame(response.gameId())).withSelfRel());
    }
}
