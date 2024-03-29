package com.tenant.aspect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.service.BaseSession;
import com.base.util.Log;

/**
 * @author Muhil kennedy
 * Aspect to add tenantFilter based on current session.
 * Adds tenantId to all hibernate jpa queries under dao packages only.
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantAspect {

	@Autowired
	private BaseSession baseSession;

	@PersistenceContext
	private EntityManager entityManager;

//	@Pointcut("execution(public * com.*.serviceimpl.*.*(..))")
//	protected void baseTenantAwareDao() {
//
//	}
	
	@Pointcut("execution(public * com.*.api.*.*(..))") // or we can change to check for within @RestController annotation
	protected void tenantAwareDao() {

	}

	@Around(value = "tenantAwareDao()")
	public Object enableTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		Session session = null;
		try {
			session = entityManager.unwrap(Session.class);
			// Enable the filter
			Filter filter = session.enableFilter("tenantFilter");
			// Set the parameter
			filter.setParameter("tenantId", baseSession.getTenantId());
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.tenant.error("Error enabling tenantFilter : " + ex.getMessage());

		}
		// Proceed with the joint point
		Object obj = joinPoint.proceed();
		if (session != null) {
			session.disableFilter("tenantFilter");
		}
		// Return
		return obj;
	}
}