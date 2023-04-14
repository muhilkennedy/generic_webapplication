package com.platform.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.platform.entity.BaseObject;
import com.platform.util.Log;

/**
 * @author Muhil
 * Create a initializer in app module for diff objects as required.
 */
public class CacheStore implements CacheService {
	
	private Cache<String, BaseObject> cache;
	
	public CacheStore() {
		this.cache = CacheBuilder.newBuilder().maximumSize(MAX_CACHED_OBJECTS)
				.expireAfterWrite(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES)
				.expireAfterAccess(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES).build();
	}

	@Override
	public BaseObject get(String key) {
		Log.tenant.debug(String.format("Fetching key %s from cache", key));
		return cache.getIfPresent(key);
	}

	@Override
	public void add(String key, BaseObject value) {
		Log.tenant.debug(String.format("Cached key %s ", key));
		cache.put(key, value);
	}

	@Override
	public void evict(String key) {
		Log.tenant.debug(String.format("Evicted key %s from cache", key));
		cache.invalidate(key);
	}

	@Override
	public void evictAll() {
		Log.tenant.debug("Clearing cache");
		Log.tenant.debug("Cahce size : " + cache.size());
		cache.invalidateAll();
	}

	@Override
	public void add(BaseObject value) {
		Log.tenant.debug(String.format("Cached key %s ", value.getObjectId()));
		this.add(value.getObjectId(), value);
	}

}
