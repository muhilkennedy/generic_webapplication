package com.tenant.service;

import com.base.service.BaseService;
import com.tenant.entity.Tenant;

/**
 * @author Muhil
 *
 */
public interface TenantService extends BaseService {
	
	Tenant findByUniqueName(String uniqueName);
	
}
