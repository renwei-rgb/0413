package com.tss.config.test;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component

public class Demo {


        @Component
        public class TestPrint {

                @Autowired
                private DataSource dataSource;

                @PostConstruct
                public void init() {
                        System.out.println("【Hikari 数据源测试】");
                        System.out.println("数据源类型: " + dataSource.getClass().getName());
                        if (dataSource instanceof HikariDataSource) {
                                HikariDataSource hikari = (HikariDataSource) dataSource;
                                System.out.println("主模块数据库地址: " + hikari.getJdbcUrl());
                        }
                        try (Connection connection = dataSource.getConnection()) {
                                System.out.println("数据库连接是否关闭: " + connection.isClosed());
                        } catch (SQLException e) {
                                System.out.println("获取数据库连接失败！");
                                e.printStackTrace();
                        }
                }

        }}
