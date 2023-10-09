package com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.service.FilterUtil;
import com.base.util.Log;

/**
 * @author Muhil
 *
 */
@Configuration
public class UserFilterConfiguration {
	@Autowired
	private UserSecurityFilter userFilter;

	@Bean
	public FilterRegistrationBean<UserSecurityFilter> UserSecurityFilterRegistration() {
		Log.user.info("----- User Filter Registrarion -----");
		FilterRegistrationBean<UserSecurityFilter> registration = new FilterRegistrationBean<UserSecurityFilter>();
		registration.setFilter(userFilter);
		registration.addUrlPatterns(FilterUtil.getValidateUserTokenUrlPatterns());
		return registration;
	}
}
