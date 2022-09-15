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
import org.springframework.stereotype.Component;

import com.base.service.BaseSession;
import com.base.util.Log;

/**
 * @author Muhil kennedy
 * Aspect to add tenantFilter based on current session
 */
@Aspect
@Component
public class TenantAspect {

	@Autowired
	private BaseSession baseSession;

	@PersistenceContext
	private EntityManager entityManager;

	@Pointcut("execution(public * com.base.dao..*(..))")
	protected void baseTenantAwareDao() {

	}
	
	@Pointcut("execution(public * com.tenant.dao..*(..))")
	protected void tenantAwareDao() {

	}

	@Around(value = "baseTenantAwareDao() || tenantAwareDao()")
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