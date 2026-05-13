package com.app.msfriendship.client;

import com.app.msfriendship.dto.external.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-client", url = "http://localhost:8081/api/v1/usuarios")
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable Long id);
}