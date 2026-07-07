package com.app.msnotification.assembler;

import com.app.msnotification.controller.NotificationController;
import com.app.msnotification.dto.NotificationResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NotificationAssembler implements RepresentationModelAssembler<NotificationResponse, EntityModel<NotificationResponse>> {

    @Override
    @Nonnull
    public EntityModel<NotificationResponse> toModel(@Nonnull NotificationResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(NotificationController.class).getAllByUserId(response.userId(), null)).withRel("user-notifications"));
    }
}
