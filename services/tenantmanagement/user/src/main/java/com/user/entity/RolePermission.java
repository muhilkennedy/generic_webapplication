package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "ROLEPERMISSION")
@ClassMetaProperty(code = "RP")
public class RolePermission extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "PERMISSIONID")
	private int permissionId;
	
	@Column(name = "ROLEID")
	private String roleId;

	@ManyToOne
	@JoinColumn(name = "PERMISSIONID", nullable = false, insertable = false, updatable = false)
	private Permission permission;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}