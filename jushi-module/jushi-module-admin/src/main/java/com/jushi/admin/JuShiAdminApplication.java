package com.jushi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.jushi.security.client","com.jushi.admin","com.jushi.api"})
public class JuShiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuShiAdminApplication.class, args);
    }
}
