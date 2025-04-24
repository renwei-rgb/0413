package com.tss.config.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatasourceProperties.class)
public class DatasourceAutoConfiguration {

    @Bean // 使用不同的 Bean 名称
    // 将该bean设置为首选bean
//    @Primary
    // 创建一个数据源bean
    public DataSource dataSource(DatasourceProperties properties) {
        // 创建一个Hikari数据源
        HikariDataSource dataSource = new HikariDataSource();
        // 设置数据源连接的URL
        dataSource.setJdbcUrl(properties.getUrl());
        // 设置数据源连接的用户名
        dataSource.setUsername(properties.getUsername());
        // 设置数据源连接的密码
        dataSource.setPassword(properties.getPassword());
        // 设置数据源连接的驱动类名
        dataSource.setDriverClassName(properties.getDriverClassName());
        // 返回数据源

//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/atm_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Tokyo");
//        dataSource.setUsername("root");
//        dataSource.setPassword("rw123");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // 其他可选的连接池参数也可以写死
        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTimeout(5000);
        return dataSource;

    }
}
