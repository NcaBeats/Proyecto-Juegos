package com.app.msreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.app.msreview",
        "com.app.exception"
})
@EnableFeignClients
@EnableJpaAuditing
public class MsReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReviewApplication.class, args);
    }

}
