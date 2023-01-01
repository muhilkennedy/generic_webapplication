package com.base.generator;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import com.base.entity.BaseObject;

/**
 * @author Muhil Kennedy
 * Return RootId value for redis key generation
 */
public class RedisKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		if (params.length > 0) {
			/*
			 * ideally pick the fist param considering as BaseObject will be the only param
			 * from BaseService for crud operations.
			 */
			if (params[0] instanceof BaseObject) {
				BaseObject baseObject = (BaseObject) params[0];
				return baseObject.getObjectId();
			}
			return String.valueOf(params[0]);
		}
		return target.getClass().getSimpleName() + "_" + method.getName() + "_"
				+ StringUtils.arrayToDelimitedString(params, "_");
	}

	public static boolean isPrimitiveTypes(Class<?> fieldClass) {
		if (ClassUtils.isPrimitiveOrWrapper(fieldClass)) {
			return true;
		}
		return false;
	}

	public static boolean isStringFieldClass(Class<?> fieldClass) {
		return fieldClass.equals(String.class);
	}

}
