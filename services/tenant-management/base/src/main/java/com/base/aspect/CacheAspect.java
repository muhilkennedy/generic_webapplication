package com.base.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.annotation.Cacheble;
import com.base.entity.BaseObject;
import com.base.service.CacheService;

/**
 * @author Muhil
 */
@Aspect
@Component
public class CacheAspect {

	@Autowired
	private CacheService cacheService;

	@Pointcut("@within(com.base.annotation.Cacheble) || @annotation(com.base.annotation.Cacheble)")
	protected void cachablePointCut() {

	}

	@Around(value = "cachablePointCut ()")
	public Object enableTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Cacheble cache = method.getAnnotation(Cacheble.class);
		// evict and proceed with operation
		if (cache.evict()) {
			if (StringUtils.isNotBlank(cache.key())) {
				cacheService.evict(cache.key());
			} else {
				Object[] params = joinPoint.getArgs();
				if (params.length > 0 && params[0] instanceof String) {
					cacheService.evict(params[0].toString());
				} else if (params.length > 0 && params[0] instanceof BaseObject) {
					cacheService.evict(((BaseObject) params[0]).getObjectId());
				} else {
					throw new UnsupportedOperationException("Eviction Failed");
				}
			}
			return joinPoint.proceed();
		}
		Object resultObj = null;
		// Fetch from cache if available
		if (StringUtils.isNotBlank(cache.key())) {
			resultObj = cacheService.get(cache.key());
		} else {
			Object[] params = joinPoint.getArgs();
			if (params.length > 0 && params[0] instanceof String) {
				resultObj = cacheService.get(params[0].toString());
			} else if (params.length > 0 && params[0] instanceof BaseObject) {
				resultObj = cacheService.get(((BaseObject) params[0]).getObjectId());
			} else {
				throw new UnsupportedOperationException("Updation Failed");
			}
		}
		if (resultObj != null && !cache.update()) {
			return resultObj;
		}
		// else execute method to fire query
		resultObj = joinPoint.proceed();
		// persist new values
		if (resultObj != null) {
			if (resultObj instanceof BaseObject) {
				if (StringUtils.isNotBlank(cache.key())) {
					cacheService.add(cache.key(), (BaseObject) resultObj);
				} else if (resultObj instanceof BaseObject) {
					cacheService.add((BaseObject) resultObj);
				} else {
					throw new UnsupportedOperationException("Caching Failed");
				}
			}
		}
		return resultObj;
	}

}
