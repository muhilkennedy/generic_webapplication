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
@Filter(name = "tenantFilter", condition = "TENANTID = :tenantId")
public class MultiTenantEntity {
	
	@Column(name = "TENANTID")
	private String tenantId;

	@Column(name = "TIMEUPDATED")
	private Long timeUpdated;

	@Column(name = "TIMECREATED")
	private Long timeCreated;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active = true;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getTimeUpdated() {
		return timeUpdated;
	}

	public void setTimeUpdated(Long timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

	public Long getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Long timeCreated) {
		this.timeCreated = timeCreated;
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
