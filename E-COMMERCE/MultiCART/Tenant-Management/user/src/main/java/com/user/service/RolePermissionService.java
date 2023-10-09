package com.user.service;

import java.util.List;

import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.messages.RoleRequest;

/**
 * @author Muhil
 */
public interface RolePermissionService {

	List<Role> findAllRoles();

	List<Permission> findAllPermission();

	List<Permission> findAllPermission(Long roleId);

	default Role createRole(RoleRequest roleRequest) {
		return null;
	};

}
