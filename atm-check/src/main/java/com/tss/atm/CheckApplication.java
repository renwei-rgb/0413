package com.tss.atm;

import com.tss.config.config.DatasourceAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.tss.atm.config")
//@SpringBootApplication
@EnableDiscoveryClient
@Import(DatasourceAutoConfiguration.class)
//@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })

@MapperScan("com.tss.atm.mapper")
//@ComponentScan(basePackages = {"com.tss", "com.tss.atm"})
//@ComponentScan(basePackages = {"com.tss.atm.config", "com.tss.atm"})
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
} 