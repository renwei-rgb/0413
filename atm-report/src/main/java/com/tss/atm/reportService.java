package com.tss.atm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tss.atm.report.mapper")
public class reportService {
    public static void main(String[] args) {
        SpringApplication.run(reportService.class, args);
    }
}
