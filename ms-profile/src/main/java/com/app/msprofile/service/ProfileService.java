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

    public ProfileResponse findById (Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con el id: " + userId));
        UserResponse userResponse = userClient.findById(userId);
        return profileMapper.toResponse(profile, userResponse);
    }

    public ProfileResponse findByNickname (String nickname) {
        Profile profile = profileRepository.findByNickname(nickname).orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con el nickname: " + nickname));
        UserResponse userResponse = userClient.findById(profile.getUserId());
        return profileMapper.toResponse(profile, userResponse);
    }
    @Transactional
    public ProfileResponse save (ProfileRequest profileRequest) {
        // Verificar que el usuario exista en el servicio ms-usuario (dejar que las excepciones de Feign propaguen)
        UserResponse userResponse = userClient.findById(profileRequest.userId());

        // Mapear, guardar y devolver
        Profile profile = profileMapper.toEntity(profileRequest, userResponse);
        Profile saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved, userResponse);
    }
}
