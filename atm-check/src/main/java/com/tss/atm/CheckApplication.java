package com.tss.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@SpringBootApplication(scanBasePackages = "com.tss")
@SpringBootApplication
@EnableDiscoveryClient
@Import(com.tss.DatasourceAutoConfiguration.class)
@MapperScan("com.tss.atm.mapper")
//@ComponentScan(basePackages = {"com.tss", "com.tss.atm"})
@ComponentScan(basePackages = {"com.tss"})
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
} 