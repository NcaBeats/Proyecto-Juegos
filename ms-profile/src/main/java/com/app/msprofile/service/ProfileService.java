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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserClient userClient;

    
    public Page<ProfileResponse> findAll (Pageable pageable) {
        log.debug("Obteniendo perfiles - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return profileRepository.findAll(pageable).map(profile -> {
            UserResponse userResponse = userClient.findById(profile.getUserId());
            return profileMapper.toResponse(profile, userResponse);
        });
    }

    public ProfileResponse findById (Long id) {
        log.debug("Obteniendo perfil por id: {}", id);
        Profile profile = profileRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id: " + id) );
        UserResponse userResponse = userClient.findById(profile.getUserId());
        log.debug("Perfil encontrado id: {} userId: {}", profile.getUserId(), profile.getUserId());
        return profileMapper.toResponse(profile, userResponse);
    }

    public ProfileResponse findByNickname(String nickname) {
        log.debug("Buscando perfil por nickname: {}", nickname);
        return profileRepository.findByNickname(nickname)
                .map(profile -> profileMapper
                        .toResponse(profile, userClient.findById(profile.getUserId()))
        ).orElseThrow( () -> new EntityNotFoundException("Perfil no encontrado con el nickname: " + nickname) );
    }

    @Transactional
    public ProfileResponse save (ProfileRequest profileRequest) {
        log.info("Creando perfil userId={}", profileRequest.userId());
        if (profileRepository.existsByUserId(profileRequest.userId())){
            throw new IllegalStateException("Ya existe un perfil con el id: " + profileRequest.userId());
        }
        UserResponse userResponse = userClient.findById(profileRequest.userId());
        Profile profile = profileMapper.toEntity(profileRequest);
        Profile profileSaved = profileRepository.save(profile);
        log.info("Perfil creado id={} userId={}", profileSaved.getUserId(), profileSaved.getUserId());
        return profileMapper.toResponse(profileSaved,userResponse);
    }

    @Transactional
    public ProfileResponse update (ProfileRequest profileRequest){
        log.info("Actualizando perfil userId={}", profileRequest.userId());
        Profile profile = profileRepository.findByUserId(profileRequest.userId()).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + profileRequest.userId()) );
        profile.update(profileRequest);
        UserResponse userResponse = userClient.findById(profileRequest.userId());
        log.info("Perfil actualizado userId={}", profileRequest.userId());
        return profileMapper.toResponse(profile,userResponse);
    }

    @Transactional
    public void delete (Long userId){
        log.info("Eliminando perfil userId={}", userId);
        Profile profile = profileRepository.findByUserId(userId).orElseThrow( ()-> new EntityNotFoundException("Perfil no encontrado con el id del usuario: " + userId) );
        profileRepository.delete(profile);
        log.info("Perfil eliminado userId={}", userId);
    }
}
