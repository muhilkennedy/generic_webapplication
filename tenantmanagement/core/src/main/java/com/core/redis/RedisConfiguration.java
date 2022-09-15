package com.core.redis;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.base.generator.RedisKeyGenerator;
import com.base.util.Log;

/**
 * @author Muhil Kennedy
 *
 */
@Profile(value = { "prod" })
@Configuration
@EnableCaching
public class RedisConfiguration {

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
		Log.core.info("----- Initializing REDIS Cache Manager -----");
		RedisCacheConfiguration config = RedisCacheConfiguration
				.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
				.prefixCacheNameWith(this.getClass().getSimpleName() + ".")
				.entryTtl(Duration.ofHours(1))
				.disableCachingNullValues();
		/*
		 * RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
		 * // default cache can be used by removing spring devtools
		 * .prefixCacheNameWith(this.getClass().getSimpleName() + ".")
		 * .entryTtl(Duration.ofHours(1)) .disableCachingNullValues();
		 *	//Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
			//redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
			//redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
			//redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		 */
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
	}
	
	@Bean("RedisKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new RedisKeyGenerator();
    }

}
