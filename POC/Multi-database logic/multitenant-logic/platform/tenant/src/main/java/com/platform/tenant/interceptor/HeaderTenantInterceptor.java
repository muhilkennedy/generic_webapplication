package com.platform.tenant.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.platform.base.service.BaseService;
import com.platform.tenant.config.ThreadTenantStorage;

@Component
public class HeaderTenantInterceptor implements WebRequestInterceptor {
	
	@Autowired
	BaseService baseService;

    public static final String TENANT_HEADER = "X-tenant";

    @Override
    public void preHandle(WebRequest request) throws Exception {
    	baseService.setTenantId(request.getHeader(TENANT_HEADER));
    	ThreadTenantStorage.setTenantId(TENANT_HEADER);
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