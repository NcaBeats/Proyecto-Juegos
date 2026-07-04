package com.app.msfriendship.assembler;

import com.app.msfriendship.controller.FriendshipController;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.model.FriendshipStatus;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FriendshipAssembler implements RepresentationModelAssembler<FriendshipResponse, EntityModel<FriendshipResponse>> {

    @Override
    @Nonnull
    public EntityModel<FriendshipResponse> toModel(@Nonnull FriendshipResponse response) {
        var entityModel = EntityModel.of(response,
                linkTo(methodOn(FriendshipController.class).getFriends(response.userId())).withRel("friends"),
                linkTo(methodOn(FriendshipController.class).getPendingRequests(response.userId())).withRel("pending")
        );

        if (response.status() == FriendshipStatus.PENDING) {
            entityModel.add(
                    linkTo(methodOn(FriendshipController.class).acceptRequest(response.id(), null)).withRel("accept"),
                    linkTo(methodOn(FriendshipController.class).rejectRequest(response.id(), null)).withRel("reject")
            );
        }

        return entityModel;
    }
}
