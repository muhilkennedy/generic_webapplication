package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.util.Log;
import com.user.security.UserFilter;

/**
 * @author Muhil
 *
 */
@Configuration
public class UserConfiguation {

	@Autowired
	private UserFilter userFilter;

	@Bean
	public FilterRegistrationBean<UserFilter> UserFilterRegistration() {
		Log.user.info("----- User Filter Registered -----");
		FilterRegistrationBean<UserFilter> registration = new FilterRegistrationBean<UserFilter>();
		registration.setFilter(userFilter);
		registration.addUrlPatterns("/tenant/auth/*", "/user/auth/*");
		return registration;
	}
}
