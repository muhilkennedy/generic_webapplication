package com.base.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.base.hiberbate.config.TenantEntityListener;

/**
 * @author Muhil Kennedy
 * Multitenant discriminator column tenantId.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(TenantEntityListener.class)
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenantId = :tenantId")
public class MultiTenantEntity {
	
	@Column(name = "TENANTID")
	private String tenantId;

	@Column(name = "TIMEUPDATED")
	private long timeUpdated;

	@Column(name = "TIMECREATED")
	private long timeCreated;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "VERSION")
	private long version;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active = true;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getTimeUpdated() {
		return timeUpdated;
	}

	public void setTimeUpdated(long timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	/*@PrePersist
	protected void prePersist() {
		if (timeCreated == null) {
			this.setTimeCreated(System.currentTimeMillis());
		}
		this.setTimeUpdated(System.currentTimeMillis());
		if(modifiedBy == null) {
			this.modifiedBy = "SYSTEM";
		}
	}

	@PreUpdate
	protected void preUpdate() {
		this.setTimeUpdated(System.currentTimeMillis());
	}*/

}
