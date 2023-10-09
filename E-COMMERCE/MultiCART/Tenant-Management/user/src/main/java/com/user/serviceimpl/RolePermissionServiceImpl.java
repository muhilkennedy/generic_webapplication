package com.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.user.dao.RolePermissionDaoService;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.entity.RolePermission;
import com.user.messages.RoleRequest;
import com.user.service.RolePermissionService;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("RolePermissionService")
@Primary
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	private RolePermissionDaoService rpDaoService;
	
	@Override
	public List<Role> findAllRoles() {
		return rpDaoService.findAllRoles();
	}

	@Override
	public List<Permission> findAllPermission() {
		return rpDaoService.findAllPermission();
	}

	@Override
	public List<Permission> findAllPermission(Long roleId) {
		return rpDaoService.findAllPermission(roleId);
	}
	
	@Override
	public Role createRole(RoleRequest roleRequest) {
		Role role = new Role();
		role.setRolename(roleRequest.getName());
		rpDaoService.saveRole(role);
		List<Permission> permissionsMap = findAllPermission().stream()
				.filter(permission -> roleRequest.getPermissionIds().contains(permission.getRootid())).toList();
		List<RolePermission> rp = permissionsMap.stream().map(permission -> rpDaoService
				.saveRolePermissionMap(new RolePermission(permission, role))).toList();
		role.setPermissions(rp);
		return role;
	}

}
