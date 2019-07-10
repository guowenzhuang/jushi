package com.jushi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.jushi.security","com.jushi.gateway"})
public class JuShiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuShiGatewayApplication.class, args);
    }

}
