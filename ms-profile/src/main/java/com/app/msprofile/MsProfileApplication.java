package com.app.msprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.app.msprofile",
        "com.app.exception"
})
@EnableJpaAuditing
@EnableFeignClients
public class MsProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProfileApplication.class, args);
    }

}
