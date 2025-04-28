package com.tss.atm.user.config;


import com.tss.config.config.DatasourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class TestPrint {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DatasourceProperties properties;

    @PostConstruct
    public void init() {
        System.out.println("数据库地址: " + properties.getUrl());
    }

    public class TestPrint1 {

        HikariDataSource hikari = (HikariDataSource) dataSource;

        @PostConstruct
        public void init() {
            System.out.println("数据库地址: " + hikari.getJdbcUrl());
        }


    }

}
