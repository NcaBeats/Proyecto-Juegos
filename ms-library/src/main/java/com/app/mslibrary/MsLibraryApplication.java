package com.app.mslibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.app.mslibrary",
        "com.app.exception"
})
@EnableFeignClients
@EnableJpaAuditing
public class MsLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLibraryApplication.class, args);
    }

}
