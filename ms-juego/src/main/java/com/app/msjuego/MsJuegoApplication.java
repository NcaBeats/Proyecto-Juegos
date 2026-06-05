package com.app.msjuego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.app.msjuego",
        "com.app.exception"
})
@EnableJpaAuditing
public class MsJuegoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsJuegoApplication.class, args);
    }

}
