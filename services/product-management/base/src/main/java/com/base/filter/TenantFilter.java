package com.base.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.util.Constants;
import com.base.util.Log;
import com.platform.entity.Tenant;
import com.platform.entity.TenantDetails;
import com.platform.exception.TenantException;
import com.platform.service.TenantService;
import com.platform.session.PlatformBaseSession;

/**
 * @author Muhil
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter  {
	
	//move to config file
	private static List<String> Whitelisted_URI = Arrays.asList("/actuator/health","/actuator/metrics");
	
    @Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return Whitelisted_URI.parallelStream().peek(uri -> Log.base.info("Request URI - " + request.getRequestURI()))
				.anyMatch(uri -> request.getRequestURI().contains(uri));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// trusted subnet check
		
		//check for null tenant header
		String tenantUniqueName = request.getHeader(Constants.TENANT_HEADER);
		if(StringUtils.isBlank(tenantUniqueName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Tenant Header is Empty");
			return;
		}
		Tenant tenant = null;
		try {
			tenant = TenantService.findByUniqueName(tenantUniqueName);
		} catch (TenantException e) {
			Log.base.error("TenantFilter :: Error fetching tenant : " + e.getMessage());
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Unexpected error fetching Tenant : " + tenantUniqueName);
			return;
		}
		if(tenant == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Tenant Not Found");
			return;
		}
		if (!tenant.isActive()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Tenant not Active");
			return;
		}
		PlatformBaseSession.setTenant(tenant);
		TenantDetails td = tenant.getTenantDetail();
		//check valid tenant origins
		Log.base.debug("Tenant filter validation successful");
		filterChain.doFilter(request, response);
	}


}
