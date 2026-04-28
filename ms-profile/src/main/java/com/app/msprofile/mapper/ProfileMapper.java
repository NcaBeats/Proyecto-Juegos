package com.app.msprofile.mapper;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toEntity(ProfileRequest request){
        return Profile.builder()
                .userId(request.userId())
                .nickname(request.nickname())
                .avatar(request.avatar())
                .bio(request.bio())
                .build();
    }
    public ProfileResponse toResponse (Profile profile){
        return ProfileResponse.builder()
                .userId(profile.getUserId())
                .nickname(profile.getNickname())
                .avatar(profile.getAvatar())
                .bio(profile.getBio())
                .fecha_registro(profile.getFecha_registro())
                .build();
    }
}
