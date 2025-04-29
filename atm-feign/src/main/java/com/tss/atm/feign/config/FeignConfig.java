package com.tss.atm.feign.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // 添加自定义请求头，跳过 CSRF 检查
            requestTemplate.header("X-Requested-With", "XMLHttpRequest");
        };
    }
} 