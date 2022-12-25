package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.util.FilterUtil;
import com.base.util.Log;
import com.user.security.UserTokenFilter;

/**
 * @author Muhil
 *
 */
@Configuration
public class UserConfiguation {

	@Autowired
	private UserTokenFilter userFilter;

	@Bean
	public FilterRegistrationBean<UserTokenFilter> UserFilterRegistration() {
		Log.user.info("----- User Filter Registered -----");
		String[] urlPatterns = FilterUtil.getAuthUrlPatterns();
		FilterRegistrationBean<UserTokenFilter> registration = new FilterRegistrationBean<UserTokenFilter>();
		registration.setFilter(userFilter);
		registration.addUrlPatterns(urlPatterns);
		return registration;
	}
}
