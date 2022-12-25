package com.tenant.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.service.BaseSession;
import com.base.util.Constants;
import com.base.util.Log;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.serviceimpl.TenantServiceImpl;
import com.tenant.util.TenantMessageKeys;

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
	private MessageSource messageSource;
	
	@Autowired
	private TenantServiceImpl tenantService;
	
	//move to config file
	private static List<String> Whitelisted_URI = Arrays.asList("/actuator/health","/actuator/metrics","/favicon.ico");
	
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request)
    {
        return Whitelisted_URI.parallelStream().filter(uri -> request.getRequestURI().contains(
            uri)).findAny().isPresent();
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		Log.tenant.info("Request URI - " + requestUri);
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
		baseSession.setLocale(tenant.getLocale());
		baseSession.setTimeZone(tenant.getTimeZone());
		if (!tenant.isActive()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					messageSource.getMessage(TenantMessageKeys.INACTVE.getKey(),
							new String[] { tenant.getTenantName() }, baseSession.getLocale()));
			return;
		}
		baseSession.setTenantInfo(tenant);
		TenantDetails td = tenant.getTenantDetail();
			//check valid tenant origins
		Log.tenant.debug("Tenant filter validation successful");
		filterChain.doFilter(request, response);
	}

}
