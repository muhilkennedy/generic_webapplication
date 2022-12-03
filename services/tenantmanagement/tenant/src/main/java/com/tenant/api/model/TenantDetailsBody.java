package com.tenant.api.model;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class TenantDetailsBody {
	
	@NotBlank
	@Length(max=12)
	private String tenantContact;
	
	@NotBlank
	@MatchesPattern(value="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String tenantEmail;

	@NotBlank
	@MatchesPattern(value="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String businessEmail;

	@NotBlank
	private String businessEmailPassword;
	
	@NotBlank
	private String tenantStreet;
	
	@NotBlank
	private String tenantCity;
	
	@NotBlank
	private String tenantPin;

	public String getTenantContact() {
		return tenantContact;
	}

	public void setTenantContact(String tenantContact) {
		this.tenantContact = tenantContact;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public String getBusinessEmailPassword() {
		return businessEmailPassword;
	}

	public void setBusinessEmailPassword(String businessEmailPassword) {
		this.businessEmailPassword = businessEmailPassword;
	}

	public String getTenantStreet() {
		return tenantStreet;
	}

	public void setTenantStreet(String tenantStreet) {
		this.tenantStreet = tenantStreet;
	}

	public String getTenantCity() {
		return tenantCity;
	}

	public void setTenantCity(String tenantCity) {
		this.tenantCity = tenantCity;
	}

	public String getTenantPin() {
		return tenantPin;
	}

	public void setTenantPin(String tenantPin) {
		this.tenantPin = tenantPin;
	}

}
