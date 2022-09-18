package com.tenant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenant.interceptor.HeaderTenantInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final HeaderTenantInterceptor headerTenantInterceptor;

    public WebConfiguration(HeaderTenantInterceptor headerTenantInterceptor) {
        this.headerTenantInterceptor = headerTenantInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(headerTenantInterceptor);
    }
}