package com.base.service;

import com.base.entity.BaseObject;

/**
 * @author muhil
 *
 * @param generic object types can be stored in cache
 */
public interface CacheService {

	public BaseObject get(String key);

	void add(String key, BaseObject value);
	
	void add(BaseObject value);
	
	void evict(String key);
	
	void evictAll();

}
