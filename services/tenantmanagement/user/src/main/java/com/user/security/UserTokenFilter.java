package com.user.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.service.BaseSession;
import com.base.util.JWTUtil;
import com.base.util.PropertiesUtil;
import com.user.entity.User;
import com.user.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Muhil
 * Validate user based on JWT token.
 */
@Component
@Order(2)
public class UserTokenFilter implements Filter {

	@Autowired
	private BaseSession baseSession;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (PropertiesUtil.isProdDeployment() || StringUtils.isNotBlank(token)) {
			String jwtToken = JWTUtil.extractToken(token);
			if (StringUtils.isNotBlank(jwtToken)) {
				try {
					if (JWTUtil.validateToken(jwtToken)) {
						String userRootId = JWTUtil.getUserIdFromToken(jwtToken);
						if (StringUtils.isNotBlank(userRootId)) {
							User user = (User) empService.findById(userRootId);
							if (user == null) {
								httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid User");
								return;
							} else if (!user.isActive()) {
								httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "User is Inactive");
								return;
							}
							baseSession.setUserInfo(user);
							baseSession.setLocale(user.getLocale());
							chain.doFilter(request, response);
						} else {
							httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
									"Token Validation failed! Token Might be tampered!");
							return;
						}
					} else {
						httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Token Validation failed");
						return;
					}
				}
				catch(ExpiredJwtException ex) {
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Token Expired");
					return;
				}
			} else {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization Token is Missing");
				return;
			}
		} else {
			//load some default user
			httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - user filter");
			return;
		}
	}

}
