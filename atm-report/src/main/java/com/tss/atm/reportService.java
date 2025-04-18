package com.tss.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tss.atm")
public class reportService {
    public static void main(String[] args) {
        SpringApplication.run(reportService.class, args);
    }
}
