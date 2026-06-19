package com.app.mslibrary.library.client;

import com.app.mslibrary.library.dto.external.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-profile")
public interface ProfileClient {
    @GetMapping("/api/v1/profiles/{userId}")
    ProfileResponse getProfileByUserId(@PathVariable Long userId);
}
