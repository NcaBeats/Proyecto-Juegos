package com.app.msfriendship.dto;

import com.app.msfriendship.model.FriendshipStatus;
import lombok.Builder;
import java.time.Instant;

@Builder
public record FriendshipResponse(
        Long id,
        Long userId,
        Long friendId,
        FriendshipStatus status,
        Instant createdAt
) {}