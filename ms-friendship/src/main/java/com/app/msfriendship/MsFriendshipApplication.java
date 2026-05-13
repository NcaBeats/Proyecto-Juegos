package com.app.msfriendship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsFriendshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsFriendshipApplication.class, args);
    }
}