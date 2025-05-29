package com.tss.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        
        // 使用 Jackson2JsonRedisSerializer 来序列化和反序列化 redis 的 value
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        
        template.afterPropertiesSet();
        return template;
    }
} 