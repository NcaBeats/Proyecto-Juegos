package com.app.mspurchase.purchase.client;

import com.app.mspurchase.purchase.dto.external.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "ms-user-purchase", url = "http://localhost:8081/api/v1/usuarios")
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable Long id);

    @PutMapping("/{id}/balance")
    void updateBalance(@PathVariable Long id,
                       @RequestParam BigDecimal monto);
}
