package com.base.hiberbate.config;

import javax.sql.DataSource;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.service.BaseSession;

//@Component
//@Configuration
public class MultiTenantConnectionProviderImpl implements CurrentTenantIdentifierResolver {

    @Autowired
    DataSource dataSource;
    
    @Autowired
    BaseSession baseService;

	@Override
	public String resolveCurrentTenantIdentifier() {
		// TODO Auto-generated method stub
		return "tenantId";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return true;
	}



}