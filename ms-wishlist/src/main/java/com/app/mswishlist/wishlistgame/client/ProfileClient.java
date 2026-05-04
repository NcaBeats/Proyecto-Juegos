package com.app.mswishlist.wishlistgame.client;

import com.app.mswishlist.wishlistgame.dto.external.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ms-profile", url = "http://localhost:8082/api/v1/profiles")
public interface ProfileClient {
    @GetMapping("/{userId}")
    ProfileResponse getProfileByUserId(Long userId);
}
