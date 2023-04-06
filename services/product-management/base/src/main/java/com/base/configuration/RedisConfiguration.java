package com.base.configuration;

import java.time.Duration;

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

import com.base.redis.CacheCustomSerializer;
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

	// Creating Connection with Redis
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}
	
//	@Bean
//	@Primary
//	public RedisConnectionFactory reactiveRedisConnectionFactory() {
//		return new ();
//	}

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
	
//	@Bean
//	@Primary
//	public ReactiveRedisTemplate<String, Object> redisTemplate(
//			ReactiveRedisConnectionFactory connectionFactory) {
//		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//		RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
//				.newSerializationContext(new StringRedisSerializer());
//		RedisSerializationContext<String, Object> context = builder.value(serializer).build();
//		return new ReactiveRedisTemplate<>(connectionFactory, context);
//	}
//	
//    /* for basic redis commands */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate() {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setKeySerializer(defaultRedisKeySerializer());
//        template.setHashKeySerializer(defaultRedisKeySerializer());
//        template.setConnectionFactory(redisConnectionFactory());
//        return template;
//    }
//
//    
//    @Bean
//    public RedisSerializer<Object> defaultRedisKeySerializer() {
//        return new GenericJackson2JsonRedisSerializer();
//    }
//
//    @Bean
//    public RedisSerializer<Object> defaultRedisValueSerializer() {
//        return new CacheCustomSerializer();
//    }
    
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		Log.getLogger().info("----- Initializing REDIS Cache Manager -----");
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
	
//	@Bean("RedisKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return new RedisKeyGenerator();
//    }

}

