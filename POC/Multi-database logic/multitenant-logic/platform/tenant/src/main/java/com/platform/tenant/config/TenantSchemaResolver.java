package com.platform.tenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.base.service.BaseService;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver{

    private String defaultTenant ="default";
    
    @Autowired
    BaseService baseService;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t =  baseService.getTenantId();
        if(t!=null){
            return t;
        } else {
            return defaultTenant;
        	//throw new RuntimeException("TenantSchemaResolver :: Invalid Tenant Config");
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
