package com.jushi.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("com.jushi.article.mapper")
@ComponentScan(basePackages = {"com.jushi.security.client","com.jushi.article","com.jushi.api"})
public class JuShiArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuShiArticleApplication.class, args);
    }
}
