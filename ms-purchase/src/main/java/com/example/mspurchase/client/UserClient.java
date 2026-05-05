package com.example.mspurchase.client;

import com.example.mspurchase.dto.external.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-user", url = "http://localhost:8081/api/v1/usuarios")
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse getUsuarioById(@PathVariable("id") Long id);
}
