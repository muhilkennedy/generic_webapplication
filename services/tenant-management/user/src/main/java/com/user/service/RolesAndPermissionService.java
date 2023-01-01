package com.user.service;

import java.util.List;

import com.base.service.BaseService;
import com.user.entity.Permission;
import com.user.entity.Role;

/**
 * @author Muhil
 *
 */
public interface RolesAndPermissionService extends BaseService {
	
	public Role findRoleById(String rootId);
	
	public Role findRoleByUniqueName(String roleName);
	
	public Permission findPermissionById(int rootId);
	
	public Permission findPermissionByUniqueName(String permissionName);
	
	public List<Role> loadDefaultRolesForTenant();

}
