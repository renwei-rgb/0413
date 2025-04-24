package com.tss.atm.user.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class dateSource {
    @Autowired
    private DataSource dataSource;

    public String getDataSource() {
        HikariDataSource hikari = (HikariDataSource) dataSource;
        return hikari.getJdbcUrl();
    }
}
