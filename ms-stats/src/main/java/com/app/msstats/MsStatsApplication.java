package com.app.msstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "com.app.msstats",
        "com.app.exception"
})
@EnableFeignClients
public class MsStatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsStatsApplication.class, args);
    }

}
