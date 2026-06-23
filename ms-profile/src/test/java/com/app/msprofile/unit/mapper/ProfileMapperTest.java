package com.app.msprofile.unit.mapper;

import com.app.msprofile.mapper.ProfileMapper;
import com.app.msprofile.model.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.app.msprofile.support.ProfileFactory.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ProfileMapperTest {

    @InjectMocks
    ProfileMapper profileMapper;

    @Test
    void toEntity_ValidRequest_ReturnEntity() {
        var result = profileMapper.toEntity(PROFILE_REQUEST);
        assertNotNull(result);
        assertEquals(PROFILE_REQUEST.userId(), result.getUserId());
        assertEquals(PROFILE_REQUEST.nickname(), result.getNickname());
    }

    @Test
    void toResponse_ValidEntity_ReturnResponse() {
        Profile entity = createProfileEntity();
        var result = profileMapper.toResponse(entity, USER_RESPONSE);
        assertNotNull(result);
        assertEquals(entity.getUserId(), result.userId());
        assertEquals(USER_RESPONSE.nombre(), result.username());
    }

}
