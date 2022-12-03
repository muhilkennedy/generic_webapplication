package com.base.serviceimpl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.base.service.CacheService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author Muhil
 *
 * @param <T>
 * 
 * Generic cache service to hold objects. Create Bean configs for required object types.
 */
@Component
public class CacheServiceImpl<T> implements CacheService<T>{
	
	private Cache<String, T> cache;
	
	public CacheServiceImpl() {
		this.cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build();
	}
	
	public CacheServiceImpl(int expiryInMinutes, int maxSize) {
		this.cache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterWrite(expiryInMinutes, TimeUnit.MINUTES)
				.build();
	}

	@Override
	public T get(String key) {
		return cache.getIfPresent(key);
	}

	@Override
	public void add(String key, T value) {
		cache.put(key, value);
	}

	@Override
	public void evict(String key) {
		cache.invalidate(key);
	}

	@Override
	public void evictAll() {
		cache.invalidateAll();
	}

}
