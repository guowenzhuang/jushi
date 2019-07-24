package com.jushi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories
public class JuShiAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuShiAuthServerApplication.class, args);
    }
}
