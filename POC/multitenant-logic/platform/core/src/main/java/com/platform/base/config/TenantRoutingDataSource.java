package com.platform.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.platform.base.security.config.ThreadTenantStorage;
import com.platform.base.service.BaseService;

public class TenantRoutingDataSource extends AbstractRoutingDataSource{
	
	@Autowired
	BaseService baseService;

	@Override
	protected Object determineCurrentLookupKey() {
		return baseService.getTenantId();
	}

}
