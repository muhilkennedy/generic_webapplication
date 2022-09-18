package com.tenant.service;

import com.base.service.BaseService;
import com.tenant.api.model.TenantModel;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;

public interface TenantService extends BaseService{
	
	public Tenant createTenant(TenantModel tenantModel);

	TenantDetails addTenantDetail(TenantDetails tenantDetail);

	TenantOrigin addTenantOrigin(TenantOrigin tenantOrigin);

}
