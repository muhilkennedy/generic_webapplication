package com.tenant.service;

import com.base.service.BaseService;
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;

public interface TenantService extends BaseService{
	
	public Tenant createTenant(TenantRequestBody tenantModel);

	TenantDetails addTenantDetail(TenantDetails tenantDetail);

	TenantOrigin addTenantOrigin(TenantOrigin tenantOrigin);

	Tenant findTenantByUniqueName(String uniqueName);

	void toggleTenantStatus();

	void toggleTenantStatus(String tenantUniqueName) throws TenantException;

	Tenant addTenantDetails(TenantDetailsBody tenantDetails);

}
