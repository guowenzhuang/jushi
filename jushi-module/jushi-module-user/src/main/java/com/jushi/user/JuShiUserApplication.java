package com.jushi.user;

import com.jushi.api.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.jushi.security.client","com.jushi.user","com.jushi.api"})
public class JuShiUserApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(JuShiUserApplication.class, args);
    }
}
