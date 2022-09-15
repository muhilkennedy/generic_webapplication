package com.tenant.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.service.BaseSession;
import com.base.util.Constants;
import com.base.util.Log;
import com.tenant.entity.Tenant;
import com.tenant.serviceimpl.TenantServiceImpl;

/**
 * @author Muhil Kennedy
 * Tenant filter to make sure only valid clients proceed further.
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter{
	
	@Autowired
	private BaseSession baseSession;
	
	@Autowired
	private TenantServiceImpl tenantService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Log.tenant.info("Request URI - " + request.getRequestURI());
		//check for null tenant header
		String tenantUniqueName = request.getHeader(Constants.TENANT_HEADER);
		if(StringUtils.isBlank(tenantUniqueName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Tenant Header is Empty");
			return;
		}
		baseSession.setTenantId(tenantUniqueName);
		Tenant tenant = tenantService.findTenantByUniqueName(tenantUniqueName);
		if(tenant == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Tenant Not Found");
			return;
		}
		if(!tenant.isActive()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Tenant is Deactivated");
			return;
		}
		baseSession.setTenantInfo(tenant);
		baseSession.setTenantId(tenant.getRootId());
		//check valid tenant origins
		filterChain.doFilter(request, response);
	}

}
