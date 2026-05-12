package com.app.msfriendship.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FriendshipRequest(
        @NotNull(message = "El campo friendId no puede ser nulo")
        Long friendId
) {}