package com.jushi.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author 80795
 * @date 2019/7/17 20:59
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.jushi.security", "com.jushi.oss", "com.jushi.api"})
public class JuShiOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuShiOssApplication.class, args);
    }
}
