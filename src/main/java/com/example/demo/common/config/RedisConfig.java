package com.example.demo.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.io.IOException;

@Configuration
public class RedisConfig {

    // 래디슨 커넥션
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    // 래디슨 설정
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {

        // ClassPathResource를 사용하여 resources 폴더의 redisson.yaml을 직접 읽습니다.
        Resource resource = new ClassPathResource("redisson.yml");

        Config config = Config.fromYAML(resource.getInputStream());

        return Redisson.create(config);
    }

    // 템플릿
    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
