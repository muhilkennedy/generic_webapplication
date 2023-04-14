package com.mken.base.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.platform.util.Log;

/**
 * @author Muhil Kennedy
 *
 */
@Profile(value = { "prod", "dev" })
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "spring.cache", value = "enabled", havingValue = "true")
public class RedisConfiguration {
	
	@Value("${spring.redis.port}")
	private int port;

	// Creating Connection with Redis
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	// multiple bean can be configured for specific entity types with diff serializer.
	@Bean(name = "genericCacheManager")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
		redisTemplate.setDefaultSerializer(serializer);
		redisTemplate.setEnableDefaultSerializer(true);
		return redisTemplate;
	}
    
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		Log.platform.info("----- Initializing REDIS Cache Manager -----");
		Log.platform.info(String.format("----- Redis server started in port %d -----", port));
		RedisCacheConfiguration config = RedisCacheConfiguration
				.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
				.prefixCacheNameWith(this.getClass().getSimpleName() + ".")
				.entryTtl(Duration.ofHours(1))
				.disableCachingNullValues();
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
	}
	
//	@Bean("RedisKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return new RedisKeyGenerator();
//    }

}

