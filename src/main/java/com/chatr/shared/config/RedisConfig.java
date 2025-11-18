package com.chatr.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        template.setKeySerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));

        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return template;
    }
}
