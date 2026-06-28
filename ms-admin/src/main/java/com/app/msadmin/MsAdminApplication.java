package com.app.msadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAdminApplication.class, args);
    }

}
