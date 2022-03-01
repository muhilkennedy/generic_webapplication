package com.platform.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.base.service.BaseService;
import com.platform.core.service.CityService;

@Aspect
@Component
public class CityServiceAspect {
	
	@Autowired
	BaseService baseService;
	
    @Before("execution(* com.platform.core.service.CityService.*(..))&& target(cityService) ")
    public void aroundExecution(JoinPoint pjp, CityService cityService) throws Throwable {
        Session filterSession = cityService.entityManager().unwrap(Session.class);
        Filter filter = filterSession.enableFilter("tenantFilter");
        filter.setParameter("tenantId", baseService.getTenantId());
        filter.validate();
    }
}
