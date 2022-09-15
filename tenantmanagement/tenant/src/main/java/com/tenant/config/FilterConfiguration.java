package com.tenant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tenant.security.TenantFilter;

/**
 * @author Muhil Kennedy
 *
 */
@Configuration
public class FilterConfiguration {
	
	@Autowired
	TenantFilter tenantFilter;

	@Bean
	public FilterRegistrationBean<TenantFilter> RealmFilterRegistration() {
		//logger.info("Tenant Filter Registered");
		FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<TenantFilter>();
		registration.setFilter(tenantFilter);
		registration.addUrlPatterns("*");
		return registration;
	}
	
}
