package com.tenant.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.base.security.AttributeEncryptor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "TENANTBUSINESSEMAIL")
@ClassMetaProperty(code = "TBE")
public class TenantBusinessEmail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "EMAILID")
	private String emailId;

	@JsonIgnore
	@Convert(converter = AttributeEncryptor.class)
	@Column(name = "EMAILPASSWORD")
	private String emailPassword;

	@Column(name = "HOST")
	private String host;

	@Column(name = "PORT")
	private String port;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TENANTID", insertable = false, updatable = false)
	private Tenant tenant;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
