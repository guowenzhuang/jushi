package com.jushi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableDiscoveryClient
@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.jushi.security", "com.jushi.admin", "com.jushi.api"})
public class JuShiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuShiAdminApplication.class, args);
    }
}
