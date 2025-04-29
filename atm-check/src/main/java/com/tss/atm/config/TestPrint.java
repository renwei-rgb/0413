package com.tss.atm.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

    @Component
    public class TestPrint{

        public CommandLineRunner testConnection(DataSource dataSource) {
            return args -> {
                try (Connection conn = dataSource.getConnection()) {
                    System.out.println("Connection test: " + conn.isValid(2));
                }
            };
        }
    }
