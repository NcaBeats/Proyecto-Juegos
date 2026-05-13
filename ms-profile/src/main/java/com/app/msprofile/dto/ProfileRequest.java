package com.app.msprofile.dto;

import com.app.msprofile.model.TipoPerfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProfileRequest(
        @NotNull
        Long userId,

        @NotBlank
        @Size(min =6, max = 30)
        String nickname,

        String avatar,

        @Size(max = 150)
        String bio,

        @NotNull
        TipoPerfil tipoPerfil
) {
}
