package com.tenant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TENANTORIGINS")
@ClassMetaProperty(code = "ORIGIN")
public class TenantOrigin extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORIGIN")
	private String origin;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TENANTID", updatable = false, insertable = false)
	private Tenant tenant;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
