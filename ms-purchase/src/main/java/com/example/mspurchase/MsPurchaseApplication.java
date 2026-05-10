package com.example.mspurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsPurchaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsPurchaseApplication.class, args);
    }
}
