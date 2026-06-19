package com.app.msfriendship.client;

import com.app.msfriendship.dto.external.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario")
public interface UserClient {
    @GetMapping("/api/v1/usuarios/{id}")
    UserResponse getUserById(@PathVariable Long id);
}