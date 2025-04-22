package com.tss.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.tss.atm.mapper")
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
} 