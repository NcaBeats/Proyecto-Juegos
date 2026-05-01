package com.app.msprofile.client;

import com.app.msprofile.dto.external.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "http://localhost:8081/api/v1/usuarios")
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse findById (@PathVariable Long id);
}
