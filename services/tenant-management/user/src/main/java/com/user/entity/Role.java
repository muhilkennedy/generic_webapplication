package com.user.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name="ROLE")
@ClassMetaProperty(code = "ROLE")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ROLENAME")
	private String roleName;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RolePermission> permissions;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}
	
}
