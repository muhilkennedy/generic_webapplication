package com.core.redis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.base.util.Log;

import redis.embedded.RedisServer;

/**
 * @author Muhil Kennedy
 * ref: https://github.com/ozimov/embedded-redis for full impl
 */
@Profile(value = { "dev" })
@Configuration
public class EmbeddedRedisConfig {

	private RedisServer redisServer;

	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.embedded.redis.enabled}")
	private boolean startEmbeddedRedis;

	@PostConstruct
	public void startRedis() {
		if (startEmbeddedRedis) {
			this.redisServer = new RedisServer(redisPort);
			redisServer.start();
			Log.core.info("----- Embedded REDIS Server Started -----");
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (startEmbeddedRedis) {
			this.redisServer.stop();
			Log.core.info("----- Embedded REDIS Stopped -----");
		}
	}
}
