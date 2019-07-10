package com.jushi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.jushi.security", "com.jushi.web","com.jushi.api"})
public class JuShiWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuShiWebApplication.class, args);
    }
}
