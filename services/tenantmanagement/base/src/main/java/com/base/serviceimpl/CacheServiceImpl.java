package com.base.serviceimpl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.base.entity.BaseObject;
import com.base.service.CacheService;
import com.base.util.CacheUtil;
import com.base.util.Log;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author Muhil
 * Custom cache implementation to hold objects.
 */
@Component
public class CacheServiceImpl implements CacheService{
	
	private Cache<String, BaseObject> cache;
	
	public CacheServiceImpl() {
		this.cache = CacheBuilder.newBuilder().maximumSize(CacheUtil.MAX_CACHED_OBJECTS)
				.expireAfterWrite(CacheUtil.MAX_CACHE_TTL_MIN, TimeUnit.MINUTES)
				.expireAfterAccess(CacheUtil.MAX_CACHE_TTL_MIN, TimeUnit.MINUTES).build();
	}
	
	/*public CacheServiceImpl(int expiryInMinutes, int maxSize) {
		this.cache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterWrite(expiryInMinutes, TimeUnit.MINUTES)
				.build();
	}*/

	@Override
	public BaseObject get(String key) {
		Log.base.debug(String.format("Fetching key %s from cache", key));
		return cache.getIfPresent(key);
	}

	@Override
	public void add(String key, BaseObject value) {
		Log.base.debug(String.format("Cached key %s ", key));
		cache.put(key, value);
	}

	@Override
	public void evict(String key) {
		Log.base.debug(String.format("Evicted key %s from cache", key));
		cache.invalidate(key);
	}

	@Override
	public void evictAll() {
		Log.base.debug("Clearing cache");
		Log.base.debug("Cahce size : " + cache.size());
		cache.invalidateAll();
	}

	@Override
	public void add(BaseObject value) {
		Log.base.debug(String.format("Cached key %s ", value.getObjectId()));
		this.add(value.getObjectId(), value);
	}

}
