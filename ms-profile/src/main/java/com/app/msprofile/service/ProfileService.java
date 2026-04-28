package com.app.msprofile.service;

import com.app.msprofile.client.UserClient;
import com.app.msprofile.mapper.ProfileMapper;
import com.app.msprofile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserClient userClient;

}
