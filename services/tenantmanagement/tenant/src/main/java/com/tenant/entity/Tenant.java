package com.tenant.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TENANT")
@ClassMetaProperty(code = "REALM")
public class Tenant implements Serializable, BaseObject {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="realm_Id", strategy = "com.base.generator.RealmIdGenerator")
	@GeneratedValue(generator = "realm_Id")
	@Column(name = "ROOTID", updatable = false, nullable = false)
	private String rootId;
	
	@Column(name = "TENANTUNIQUENAME")
	private String tenantUniqueName;

	@Column(name = "TENANTNAME")
	private String tenantName;
	
	@Column(name = "TIMEUPDATED")
	private Long timeUpdated;

	@Column(name = "TIMECREATED")
	private Long timeCreated;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;

	@Column(name = "PURGETENANT", columnDefinition = "boolean default false")
	private boolean purgeTenant;
	
	@OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private TenantDetails tenantDetail;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TenantOrigin> tenantOrigin;

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

	public boolean isPurgeTenant() {
		return purgeTenant;
	}

	public void setPurgeTenant(boolean purgeTenant) {
		this.purgeTenant = purgeTenant;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
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
	
	public TenantDetails getTenantDetail() {
		return tenantDetail;
	}

	public void setTenantDetail(TenantDetails tenantDetail) {
		this.tenantDetail = tenantDetail;
	}

	public List<TenantOrigin> getTenantOrigin() {
		return tenantOrigin;
	}

	public void setTenantOrigin(List<TenantOrigin> tenantOrigin) {
		this.tenantOrigin = tenantOrigin;
	}

	@PrePersist
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
	}
	
	@Override
	public String getObjectId() {
		return tenantUniqueName;
	}

}
