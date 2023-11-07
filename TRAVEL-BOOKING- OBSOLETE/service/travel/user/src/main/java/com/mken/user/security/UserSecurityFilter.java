package com.mken.user.security;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.mken.base.session.BaseSession;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.service.EmployeeService;
import com.mysql.cj.util.StringUtils;
import com.platform.util.JWTUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserSecurityFilter implements Filter {
	
	@Autowired
	private EmployeeService empService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		User user = (Employee) empService.findById(1L);
		BaseSession.setUser(user);
		chain.doFilter(request, response);
//		if(StringUtils.isNullOrEmpty(token)) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization Token is Missing");
//			return;
//		}
//		String jwtToken = JWTUtil.extractToken(token);
//		if(StringUtils.isNullOrEmpty(jwtToken)) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bearer Token is Missing");
//			return;
//		}
//		if(JWTUtil.validateToken(jwtToken)) {
//			String userRootId = JWTUtil.getUserIdFromToken(jwtToken);
//			if (!StringUtils.isNullOrEmpty(userRootId)) {
//				User user = null;
//				if(JWTUtil.isEmployeeUser(jwtToken)) {
//					user = (Employee) empService.findById(Long.parseLong(userRootId));
//				}
//				else {
//					//customer user
//				}
//				if(user == null) {
//					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
//					return;
//				}
//				if(!user.isActive()) {
//					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is deactivated. please contact admin.");
//					return;
//				}
//				BaseSession.setUser(user);
//				chain.doFilter(request, response);
//			}
//			else {
//				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Token tampered");
//				return;
//			}
//		}
//		else {
//			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Token Validation failed");
//			return;
//		}
	}

}
