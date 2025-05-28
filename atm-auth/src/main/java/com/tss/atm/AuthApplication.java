package com.tss.atm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.tss.atm", "com.tss.atm.common", "com.tss.atm.feign"})
@MapperScan("com.tss.atm.auth.mapper")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.tss.atm.feign")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}