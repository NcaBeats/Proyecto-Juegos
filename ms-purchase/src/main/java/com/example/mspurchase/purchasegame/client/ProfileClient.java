package com.example.mspurchase.purchasegame.client;

import com.example.mspurchase.purchasegame.dto.external.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-profile", url = "http://localhost:8082/api/v1/profiles")
public interface ProfileClient {
    @GetMapping("/{userId}")
    ProfileResponse getProfileByUserId(@PathVariable Long userId);
}
