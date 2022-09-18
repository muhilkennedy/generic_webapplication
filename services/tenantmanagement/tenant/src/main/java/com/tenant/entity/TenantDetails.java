package com.tenant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name="TENANTDETAILS")
@ClassMetaProperty(code = "TD")
public class TenantDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "TENANTCONTACT")
	private String tenantContact;
	
	@Column(name = "TENANTEMAIL")
	private String tenantEmail;

	@Column(name = "TENANTBUSINESSEMAIL")
	private String businessEmail;

	@Column(name = "BUSINESSEMAILPASSWORD")
	private String businessEmailPassword;
	
	@Column(name = "TENANTSTREET")
	private String tenantStreet;
	
	@Column(name = "TENANTCITY")
	private String tenantCity;
	
	@Column(name = "TENANTPIN")
	private String tenantPin;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TENANTID", insertable=false, updatable=false)
	private Tenant tenant;

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
