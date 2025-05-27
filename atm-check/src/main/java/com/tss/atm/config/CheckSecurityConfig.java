package com.tss.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CheckSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 关闭 CSRF（跨站请求伪造）保护，方便接口测试
                .authorizeRequests()
                .anyRequest().permitAll(); // 允许所有请求不需要认证
        return http.build();
    }
}