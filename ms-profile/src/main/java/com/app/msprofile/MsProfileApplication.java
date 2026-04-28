package com.app.msprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MsProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProfileApplication.class, args);
    }

}
