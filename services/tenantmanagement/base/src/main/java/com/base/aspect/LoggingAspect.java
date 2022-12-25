package com.base.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.base.annotation.Loggable;
import com.base.service.BaseSession;
import com.base.util.Log;

/**
 * @author Muhil Kennedy
 * Perf and Audit logging based on Loggable annotation
 */
@Service
@Aspect
@ConditionalOnProperty(prefix = "logging.godmode", value = "enabled", havingValue = "true")
public class LoggingAspect {

	@Autowired
	private BaseSession baseSession;

//	@Pointcut("execution(* com.*.*.*.*(..))")
//	public void auditLog() {
//	}
	
	@Pointcut("@within(com.base.annotation.Loggable) || @annotation(com.base.annotation.Loggable)")
	protected void logPointCut() {

	}

//	@Pointcut("within(com.*)")
//	public void tenantPerfLog() {
//	}

	@Around(value = "logPointCut() && target(bean) && @annotation(com.base.annotation.Loggable) && @annotation(log)", argNames = "bean,log")
	public Object performanceLog(ProceedingJoinPoint joinPoint, Object bean, Loggable log) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		if (log.perf())
			stopWatch.start();
		Log.base.debug("Tenant : " + baseSession.getTenantId() + " : Log message : " + log.message());
		Log.base.debug("Tenant : " + baseSession.getTenantId() + " : Executing : " + joinPoint.getSignature().getName()
				+ " Method in class " + bean.getClass().getName());
		Log.base.debug("Tenant : " + baseSession.getTenantId() + " : Time of Execution : " + new Date().toString());
		Object obj = joinPoint.proceed();
		if (log.perf()) {
			stopWatch.stop();
			Log.base.debug("Tenant : " + baseSession.getTenantId() + " : Total time taken to execute "
					+ joinPoint.getSignature().getName() + " : " + stopWatch.getTotalTimeSeconds() + " sec(s)");
		}
		return obj;
	}
	
	/*@Before(value = "auditLog() && target(bean) && @annotation(com.base.annotation.Loggable) && @annotation(log)", argNames = "bean,log")
    public void log(JoinPoint joinPoint, Object bean, Loggable log) {
		Log.base.debug("Log message : " + log.message());
		Log.base.debug("Bean Class : " + bean.getClass().getName());
		Log.base.debug("Method Executed : " + joinPoint.getSignature().getName());
		Log.base.debug("Time of Execution : " + new Date().toString());
    }*/

}
