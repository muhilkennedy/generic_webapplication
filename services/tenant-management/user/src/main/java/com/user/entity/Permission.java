package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "PERMISSION")
@ClassMetaProperty(code = "PERMISSION")
public class Permission {

	@Id
	@Column(name = "ROOTID")
	private int rootId;

	@Column(name = "PERMISSION")
	private String permission;

	@Column(name = "ACTIVE")
	private boolean active;

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
