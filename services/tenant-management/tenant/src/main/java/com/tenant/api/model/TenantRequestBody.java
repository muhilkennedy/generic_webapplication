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

	private String tenantEmail;
	private String tenantContact;
	private String tagLine;
	
	private TenantSubscriptionModel tenantSubscription;

	public TenantSubscriptionModel getTenantSubscription() {
		return tenantSubscription;
	}

	public void setTenantSubscription(TenantSubscriptionModel tenantSubscription) {
		this.tenantSubscription = tenantSubscription;
	}

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

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantContact() {
		return tenantContact;
	}

	public void setTenantContact(String tenantContact) {
		this.tenantContact = tenantContact;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

}
