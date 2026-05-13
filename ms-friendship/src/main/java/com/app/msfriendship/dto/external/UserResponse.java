package com.app.msfriendship.dto.external;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String nombre,
        String email
) {}