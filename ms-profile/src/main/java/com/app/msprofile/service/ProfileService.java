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

    public Page<ProfileResponse> findByFiltros(Long userId,String nickname,Pageable pageable) {
        return profileRepository.findByFiltros(userId,nickname,pageable).map(profile -> {
            UserResponse userResponse = userClient.findById(profile.getUserId());
            return profileMapper.toResponse(profile, userResponse);
        });
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
        UserResponse userResponse = userClient.findById(profileRequest.userId());
        Profile profile = profileRepository.findByUserId(userResponse.id()).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + userResponse.id()) );
        profile.update(profileRequest);
        return profileMapper.toResponse(profile,userResponse);
    }

    @Transactional
    public void delete (Long userId){
        Profile profile = profileRepository.findByUserId(userId).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + userId) );
        profileRepository.delete(profile);
    }
}
