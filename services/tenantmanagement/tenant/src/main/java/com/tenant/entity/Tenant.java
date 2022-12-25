package com.tenant.entity;

import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;

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
	@JsonIgnore
	private String rootId;
	
	@Column(name = "TENANTUNIQUENAME")
	private String tenantUniqueName;

	@Column(name = "TENANTNAME")
	private String tenantName;
	
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
	
	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timeZone;
	
	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;

	@Column(name = "PURGETENANT", columnDefinition = "boolean default false")
	private boolean purgeTenant;
	
	@OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public void setTimeUpdated(long timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timZone) {
		this.timeZone = timZone;
	}

	public long getTimeUpdated() {
		return timeUpdated;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	@PrePersist
	protected void prePersist() {
		if (this.timeCreated == 0L) {
			this.setTimeCreated(System.currentTimeMillis());
		}
		this.setTimeUpdated(System.currentTimeMillis());
		if (this.modifiedBy == null) {
			this.modifiedBy = "SYSTEM";
		}
		if (this.createdBy == null) {
			this.createdBy = "SYSTEM";
		}
		if (this.locale == null) {
			this.locale = "en_US";
		}
		if (this.timeZone == null) {
			this.timeZone = TimeZone.getDefault().getID();
		}
	}

	@PreUpdate
	protected void preUpdate() {
		this.setVersion(++this.version);
		this.setTimeUpdated(System.currentTimeMillis());
	}
	
	@Override
	@JsonIgnore
	public String getObjectId() {
		return rootId;
	}

}
