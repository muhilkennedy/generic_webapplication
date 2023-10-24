package com.mken.base.aspects;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jobrunr.utils.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mken.base.util.Log;
import com.platform.annotations.Loggable;

/**
 * @author Muhil Kennedy
 * Perf and Audit logging based on Loggable annotation
 */
@Service
@Aspect
@ConditionalOnProperty(prefix = "logging.godmode", value = "enabled", havingValue = "true")
public class LoggingAspect {
	
	private static ObjectMapper mapper = new ObjectMapper(); 

	@Pointcut("@within(com.platform.annotations.Loggable) || @annotation(com.platform.annotations.Loggable)")
	protected void logPointCut() {

	}
	
	@Pointcut("within(com.mken..serviceimpl.*)")
	protected void logServicePackagesPointCut() {

	}
	
	@Around(value = "logServicePackagesPointCut()")
	public Object loggerWrapper(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Log.base.debug("Executing : {} Method in class {}", joinPoint.getSignature().getName(),
				joinPoint.getThis().getClass().getName());
		Object[] methodArguments = joinPoint.getArgs();
		AtomicInteger methodIndex = new AtomicInteger();
		Arrays.stream(methodArguments).forEach(method -> {
			try {
				Log.base.debug("Method Argument {} : {}", methodIndex.get(), mapper.writeValueAsString(method));
			} catch (JsonProcessingException e) {
				Log.base.error("Failed to convert object into json string - {}", e.getMessage());
			}
			methodIndex.incrementAndGet();
		});
		Log.base.debug("Method execution started at : {}", new Date().toString());
		Object result = joinPoint.proceed();
		Log.base.debug("Result of method {} : {}", joinPoint.getSignature().getName(), mapper.writeValueAsString(result));
		stopWatch.stop();
		Log.base.debug("Total time taken to execute Method {} : {} sec(s)", joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		return result;
	}

	@Around(value = "logPointCut() && target(bean) && @annotation(com.platform.annotations.Loggable) && @annotation(log)", argNames = "bean,log")
	public Object loggerWrapper(ProceedingJoinPoint joinPoint, Object bean, Loggable log) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Log.base.debug("Executing : {} Method in class {}", joinPoint.getSignature().getName(),
				bean.getClass().getName());
		if (StringUtils.isNotNullOrEmpty(log.message())) {
			Log.base.debug("Log message : {}", log.message());
		}
		Object[] methodArguments = joinPoint.getArgs();
		AtomicInteger methodIndex = new AtomicInteger();
		Arrays.stream(methodArguments).forEach(method -> {
			try {
				Log.base.debug("Method Argument {} : {}", methodIndex.get(), mapper.writeValueAsString(method));
			} catch (JsonProcessingException e) {
				Log.base.error("Failed to convert object into json string - {}", e.getMessage());
			}
			methodIndex.incrementAndGet();
		});
		Log.base.debug("Method execution started at : {}", new Date().toString());
		Object result = joinPoint.proceed();
		Log.base.debug("Result of method {} : {}", joinPoint.getSignature().getName(), mapper.writeValueAsString(result));
		stopWatch.stop();
		Log.base.debug("Total time taken to execute Method {} : {} sec(s)", joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		return result;
	}

}
