package com.base.hiberbate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.entity.BaseObject;
import com.base.service.CacheService;
import com.base.serviceimpl.CacheServiceImpl;

/**
 * @author muhil 
 * multiple beans can be defined specific to each module for
 * caching diff objects as required.
 */
@Configuration
public class CacheConfig {

//	@Bean
//	public CacheService<BaseObject> baseObjectCache() {
//		return new CacheServiceImpl<BaseObject>(1000 , 10);
//	}
	  
}
