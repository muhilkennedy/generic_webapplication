package com.tenant.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.base.service.BaseSession;

/**
 * Interceptors are executed after filter for fine enriching impl for http request
 * */
/**
 * @author Muhil
 *
 */
@Component
public class HeaderTenantInterceptor implements WebRequestInterceptor {
	
	@Autowired
	private BaseSession baseService;

    @Override
    public void preHandle(WebRequest request) throws Exception {
    	//baseService.setTenantId(request.getHeader(TENANT_HEADER));
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
    	baseService.clear();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    	//No-OP
    }
}