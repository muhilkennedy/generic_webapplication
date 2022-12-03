package com.tenant.api.model;

import javax.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class TenantRequestBody {

	@NotBlank
	private String tenantUniqueName;
	
	@NotBlank
	private String tenantName;
	
	public String getTenantUniqueName() {
		return tenantUniqueName;
	}
	public void setTenantUniqueName(String tenantUniqueName) {
		this.tenantUniqueName = tenantUniqueName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}
