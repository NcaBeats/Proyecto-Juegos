package com.app.msprofile.mapper;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.dto.external.UserResponse;
import com.app.msprofile.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toEntity(ProfileRequest request, UserResponse userResponse){
        return Profile.builder()
                .userId(userResponse.id())
                .nickname(request.nickname())
                .avatar(request.avatar())
                .bio(request.bio())
                .tipoPerfil(request.tipoPerfil())
                .build();
    }
    public ProfileResponse toResponse (Profile profile, UserResponse userResponse){
        return ProfileResponse.builder()
                .userId(profile.getUserId())
                .nickname(profile.getNickname())
                .avatar(profile.getAvatar())
                .bio(profile.getBio())
                .fecha_registro(profile.getFechaRegistro())
                .username(userResponse.username())
                .email(userResponse.email())
                .build();
    }
}
