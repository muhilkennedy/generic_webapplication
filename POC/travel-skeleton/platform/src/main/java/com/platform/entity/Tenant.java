package com.platform.entity;

import java.util.List;

/**
 * @author Muhil
 *
 */
public class Tenant implements BaseObject {

	private String rootId;
	private String tenantUniqueName;
	private String tenantName;
	private String locale;
	private String timeZone;
	private boolean purgeTenant;

	// common base fields
	private boolean active;
	private long timeUpdated;
	private long timeCreated;
	private String modifiedBy;
	private String createdBy;
	private long version;

	private TenantDetails tenantDetail;

	private List<TenantOrigin> tenantOrigin;
	
	@Override
	public String getObjectId() {
		return this.tenantUniqueName;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isPurgeTenant() {
		return purgeTenant;
	}

	public void setPurgeTenant(boolean purgeTenant) {
		this.purgeTenant = purgeTenant;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

}