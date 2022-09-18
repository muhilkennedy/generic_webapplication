package com.tenant.api.model;

import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;

/**
 * @author Muhil
 *
 */
public class TenantModel {

	Tenant tenant;
	TenantDetails tenantDetails;
	TenantOrigin tenantOrigin;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public TenantDetails getTenantDetails() {
		return tenantDetails;
	}

	public void setTenantDetails(TenantDetails tenantDetails) {
		this.tenantDetails = tenantDetails;
	}

	public TenantOrigin getTenantOrigin() {
		return tenantOrigin;
	}

	public void setTenantOrigin(TenantOrigin tenantOrigin) {
		this.tenantOrigin = tenantOrigin;
	}

}
