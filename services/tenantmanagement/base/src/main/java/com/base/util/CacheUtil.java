package com.base.util;

public class CacheUtil {

	public static final String TENANT_CACHE = "tenant-cache";
	public static final String USER_CACHE = "user-cache";
	public static final String REDIS_KEY_GENERATOR = "RedisKeyGenerator";
	
	public static final int MAX_CACHED_OBJECTS = 1000;
	public static final int MAX_CACHE_TTL_MIN = 10;
}
