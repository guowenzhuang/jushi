package com.jushi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("com.jushi.user.mapper")
@ComponentScan(basePackages = {"com.jushi.security.client","com.jushi.user","com.jushi.api"})
public class JuShiUserApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(JuShiUserApplication.class, args);
    }
}
