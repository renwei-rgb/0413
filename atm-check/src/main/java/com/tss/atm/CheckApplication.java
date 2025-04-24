package com.tss.atm;

import com.tss.config.config.DatasourceAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.tss.atm.config")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.tss.atm.mapper")
@Import(DatasourceAutoConfiguration.class)
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
} 