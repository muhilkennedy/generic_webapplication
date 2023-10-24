package com.mken.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Muhil
 *
 */
public class CacheUtil {
	
	public static final String DEFAULT_CACHE_NAME = "default";
	public static final String CUSTOMER_CACHE_NAME = "customer";
	public static final String EMPLOYEE_CACHE_NAME = "employee";
	public static final String DESTINATION_CACHE_NAME = "destination";
	public static final String ATTRACTION_CACHE_NAME = "attraction";
	public static final String PACKAGES_CACHE_NAME = "package";
	
	public static List<String> getAllCacheNames() throws IllegalArgumentException, IllegalAccessException{
		List<String> cacheNames = new ArrayList<String>();
		Field[] fields = CacheUtil.class.getFields();
		for (Field field : fields) {
			Object value = field.get(null);
			cacheNames.add(value.toString());
		}
		return cacheNames;
	}
	
}
