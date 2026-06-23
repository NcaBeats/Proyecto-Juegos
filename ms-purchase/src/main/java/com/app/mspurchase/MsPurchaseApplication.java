package com.app.mspurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {
        "com.app.mspurchase",
        "com.app.exception"
})
@EnableJpaAuditing
public class MsPurchaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsPurchaseApplication.class, args);
    }
}
