package com.app.msprofile.unit.controller;

import com.app.msprofile.controller.ProfileController;
import com.app.msprofile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.app.msprofile.support.ProfileFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    ProfileService profileService;

    @InjectMocks
    ProfileController profileController;

    @Test
    void findAll_ReturnsOk() {
        when(profileService.findAll(PAGEABLE)).thenReturn(Page.empty());

        var result = profileController.findAll(PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void findById_ReturnsOk() {
        when(profileService.findById(USER_ID)).thenReturn(PROFILE_RESPONSE);

        var result = profileController.findById(USER_ID);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(PROFILE_RESPONSE, result.getBody());
    }

    @Test
    void buscar_ReturnsOk() {
        when(profileService.findByNickname(NICKNAME)).thenReturn(PROFILE_RESPONSE);

        var result = profileController.buscar(NICKNAME);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(PROFILE_RESPONSE, result.getBody());
    }

    @Test
    void save_ReturnsCreated() {
        when(profileService.save(PROFILE_REQUEST)).thenReturn(PROFILE_RESPONSE);

        var result = profileController.save(PROFILE_REQUEST);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(PROFILE_RESPONSE, result.getBody());
    }

    @Test
    void update_ReturnsOk() {
        when(profileService.update(PROFILE_REQUEST)).thenReturn(PROFILE_RESPONSE);

        var result = profileController.update(PROFILE_REQUEST);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(PROFILE_RESPONSE, result.getBody());
    }

    @Test
    void delete_ReturnsNoContent() {
        doNothing().when(profileService).delete(USER_ID);

        var result = profileController.delete(USER_ID);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }

}
