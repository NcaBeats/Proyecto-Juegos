package com.app.msprofile.unit.service;

import com.app.msprofile.client.UserClient;
import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.mapper.ProfileMapper;
import com.app.msprofile.model.Profile;
import com.app.msprofile.repository.ProfileRepository;
import com.app.msprofile.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.msprofile.support.ProfileFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileMapper profileMapper;
    @Mock
    UserClient userClient;

    @InjectMocks
    ProfileService profileService;

    @Test
    void findAll_ReturnsPage() {
        List<Profile> list = List.of(PROFILE_ENTITY);
        Page<Profile> page = new PageImpl<>(list, PAGEABLE, list.size());
        when(profileRepository.findAll(PAGEABLE)).thenReturn(page);
        when(userClient.findById(PROFILE_ENTITY.getUserId())).thenReturn(USER_RESPONSE);
        when(profileMapper.toResponse(PROFILE_ENTITY, USER_RESPONSE)).thenReturn(PROFILE_RESPONSE);

        var result = profileService.findAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void findById_Found_ReturnResponse() {
        when(profileRepository.findById(USER_ID)).thenReturn(Optional.of(PROFILE_ENTITY));
        when(userClient.findById(USER_ID)).thenReturn(USER_RESPONSE);
        when(profileMapper.toResponse(PROFILE_ENTITY, USER_RESPONSE)).thenReturn(PROFILE_RESPONSE);

        var result = profileService.findById(USER_ID);

        assertNotNull(result);
        assertEquals(PROFILE_RESPONSE, result);
    }

    @Test
    void findById_NotFound_Throw() {
        when(profileRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.findById(USER_ID));
    }

    @Test
    void save_Success_ReturnResponse() {
        ProfileRequest req = PROFILE_REQUEST;
        when(profileRepository.existsByUserId(req.userId())).thenReturn(false);
        when(userClient.findById(req.userId())).thenReturn(USER_RESPONSE);
        when(profileMapper.toEntity(req)).thenReturn(PROFILE_ENTITY);
        when(profileRepository.save(PROFILE_ENTITY)).thenReturn(PROFILE_ENTITY);
        when(profileMapper.toResponse(PROFILE_ENTITY, USER_RESPONSE)).thenReturn(PROFILE_RESPONSE);

        var result = profileService.save(req);

        assertNotNull(result);
        assertEquals(PROFILE_RESPONSE, result);
    }

    @Test
    void save_AlreadyExists_Throw() {
        when(profileRepository.existsByUserId(USER_ID)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> profileService.save(PROFILE_REQUEST));
    }

    @Test
    void update_Success_ReturnResponse() {
        ProfileRequest req = PROFILE_REQUEST;
        when(profileRepository.findByUserId(req.userId())).thenReturn(Optional.of(PROFILE_ENTITY));
        when(userClient.findById(req.userId())).thenReturn(USER_RESPONSE);
        when(profileMapper.toResponse(PROFILE_ENTITY, USER_RESPONSE)).thenReturn(PROFILE_RESPONSE);

        var result = profileService.update(req);

        assertNotNull(result);
        assertEquals(PROFILE_RESPONSE, result);
    }

    @Test
    void update_NotFound_Throw() {
        when(profileRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.update(PROFILE_REQUEST));
    }

    @Test
    void delete_Success() {
        when(profileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(PROFILE_ENTITY));
        doNothing().when(profileRepository).delete(PROFILE_ENTITY);

        profileService.delete(USER_ID);

        verify(profileRepository).delete(PROFILE_ENTITY);
    }

    @Test
    void delete_NotFound_Throw() {
        when(profileRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> profileService.delete(USER_ID));
    }

}
