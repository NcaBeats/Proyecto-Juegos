package com.app.msreview.assembler;

import com.app.msreview.controller.ReviewController;
import com.app.msreview.dto.ReviewResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewAssembler implements RepresentationModelAssembler<ReviewResponse, EntityModel<ReviewResponse>> {

    @Override
    @Nonnull
    public EntityModel<ReviewResponse> toModel(@Nonnull ReviewResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(ReviewController.class).findAllByJuegoId(response.juegoId())).withRel("game-reviews"));
    }
}
