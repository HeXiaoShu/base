package com.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

/**
 * caffeine 本地缓存设置
 */
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(Duration.ofMinutes(1L))
                // 初始的缓存空间大小
                .initialCapacity(1000)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

}