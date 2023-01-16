package com.tenant.service;

import java.sql.SQLException;
import java.util.List;

import com.base.service.BaseService;
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.api.model.TenantSubscriptionModel;
import com.tenant.api.model.TenantsResponse;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;

public interface TenantService extends BaseService{
	
	public void reEvaluateTenantSession();
	
	public void reEvaluateSessionForTenant(String tenantUniqueName);
	
	public Tenant createTenant(TenantRequestBody tenantModel);

	TenantDetails addTenantDetail(TenantDetails tenantDetail);

	TenantOrigin addTenantOrigin(TenantOrigin tenantOrigin);

	Tenant findTenantByUniqueName(String uniqueName);

	void toggleTenantStatus();

	void toggleTenantStatus(String tenantUniqueName) throws TenantException;

	Tenant addTenantDetails(TenantDetailsBody tenantDetails);
	
	List<SubscriptionHistory> getTenantHistory();
	
	void updateTenantExpiry(TenantSubscriptionModel subscriptionModel);
	
	List<TenantsResponse> getAllTenants() throws SQLException, Exception;

}
