package com.app.msusuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.app.msusuario",
        "com.app.exception"
})
@EnableFeignClients
@EnableJpaAuditing
public class MsUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuarioApplication.class, args);
    }

}
