package com.tss;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import com.tss.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@SpringBootTest(classes = {ConfigApplication.class, DatasourceAutoConfiguration.class}) // 指定配置类
//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:mysql://localhost:3306/atm_db?useSSL=false&serverTimezone=UTC",
//        "spring.datasource.username=root",
//        "spring.datasource.password=rw123",
//        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
//})
//@ActiveProfiles("test")
public class DataSourceIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws Exception {
        System.out.println("数据源类型: " + dataSource.getClass().getName());
        System.out.println("数据库连接: " + dataSource.getConnection());
    }
}
