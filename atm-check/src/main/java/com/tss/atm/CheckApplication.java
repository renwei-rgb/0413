package com.tss.atm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringBootApplication(scanBasePackages = "com.tss.atm")
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
//@EnableFeignClients(basePackages = "com.tss.atm.feign")
@MapperScan("com.tss.atm.mapper")

public class CheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
}