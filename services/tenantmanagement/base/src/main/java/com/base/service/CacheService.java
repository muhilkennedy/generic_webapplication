package com.base.service;


/**
 * @author muhil
 *
 * @param <T> generic object types can be stored in cache
 */
public interface CacheService<T> {

	public T get(String key);

	void add(String key, T value);
	
	void evict(String key);
	
	void evictAll();

}
