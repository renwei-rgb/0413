package com.tss.atm.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.tss.atm.user.mapper")
public class UserService {

    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
    }
}