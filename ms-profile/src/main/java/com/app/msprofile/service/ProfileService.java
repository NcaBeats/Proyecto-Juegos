package com.app.msprofile.service;

import com.app.msprofile.client.UserClient;
import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.dto.external.UserResponse;
import com.app.msprofile.mapper.ProfileMapper;
import com.app.msprofile.model.Profile;
import com.app.msprofile.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserClient userClient;

 // Posible problema de n+1 query, pero se dejará por simplicidad del proyecto
    public Page<ProfileResponse> findAll (Pageable pageable) {
        return profileRepository.findAll(pageable).map(profile -> {
            UserResponse userResponse = userClient.findById(profile.getUserId());
            return profileMapper.toResponse(profile, userResponse);
        });
    }

    public ProfileResponse findById (Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id: " + id) );
        UserResponse userResponse = userClient.findById(profile.getUserId());
        return profileMapper.toResponse(profile, userResponse);
    }

    public ProfileResponse findByNickname(String nickname) {
        return profileRepository.findByNickname(nickname)
                .map(profile -> profileMapper
                        .toResponse(profile, userClient.findById(profile.getUserId()))
        ).orElseThrow( () -> new EntityNotFoundException("Perfil no encontrado con el nickname: " + nickname) );
    }

    @Transactional
    public ProfileResponse save (ProfileRequest profileRequest) {
        if (profileRepository.existsByUserId(profileRequest.userId())){
            throw new IllegalStateException("Ya existe un perfil con el id: " + profileRequest.userId());
        }
        UserResponse userResponse = userClient.findById(profileRequest.userId());
        Profile profile = profileMapper.toEntity(profileRequest);
        Profile profileSaved = profileRepository.save(profile);
        return profileMapper.toResponse(profileSaved,userResponse);
    }

    @Transactional
    public ProfileResponse update (ProfileRequest profileRequest){
        Profile profile = profileRepository.findByUserId(profileRequest.userId()).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + profileRequest.userId()) );
        profile.update(profileRequest);
        UserResponse userResponse = userClient.findById(profileRequest.userId());
        return profileMapper.toResponse(profile,userResponse);
    }

    @Transactional
    public void delete (Long userId){
        Profile profile = profileRepository.findByUserId(userId).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + userId) );
        profileRepository.delete(profile);
    }
}
