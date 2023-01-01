package com.user.serviceimpl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseObject;
import com.base.security.Roles;
import com.user.dao.PermissionRepository;
import com.user.dao.RolePermissionRepository;
import com.user.dao.RoleRepository;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.entity.RolePermission;
import com.user.service.RolesAndPermissionService;

/**
 * @author Muhil
 *
 */
@Service
public class RolesAndPermissionServiceImpl implements RolesAndPermissionService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionsRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public Object save(BaseObject obj) {
		if (obj instanceof Role) {
			return roleRepository.save((Role) obj);
		} else if (obj instanceof Permission) {
			return permissionsRepository.save((Permission) obj);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object saveAndFlush(BaseObject obj) {
		if (obj instanceof Role) {
			return roleRepository.saveAndFlush((Role) obj);
		} else if (obj instanceof Permission) {
			return permissionsRepository.saveAndFlush((Permission) obj);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object findById(Object rootId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(BaseObject obj) {
		if (obj instanceof Role) {
			roleRepository.delete((Role) obj);
		} else if (obj instanceof Permission) {
			permissionsRepository.delete((Permission) obj);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Role findRoleById(String rootId) {
		return roleRepository.findById(rootId).get();
	}

	@Override
	public Role findRoleByUniqueName(String roleName) {
		return roleRepository.findRoleByUniqueName(roleName);
	}

	@Override
	public Permission findPermissionById(int rootId) {
		return permissionsRepository.findById(rootId).get();
	}

	@Override
	public Permission findPermissionByUniqueName(String permissionName) {		
		return permissionsRepository.findPermissionByUniqueName(permissionName);
	}
	
	public List<Role> findAllRolesForTenant(){
		return roleRepository.findAll();
	}

	@Override
	public List<Role> loadDefaultRolesForTenant() {
		List<Roles> defaultRoles = Roles.getDefaultRoles();
		List<Role> tenantRoles = findAllRolesForTenant();
		if(tenantRoles == null || tenantRoles.isEmpty()) {
			defaultRoles.stream().forEach(role -> {
				Role newRole = new Role();
				newRole.setRoleName(role.getRoleUniqueName());
				tenantRoles.add((Role) saveAndFlush(newRole));
				Stream.of(role.getRolePermissions()).forEach(rolePermission -> {
					Permission permission = findPermissionByUniqueName(rolePermission.getPermissionUniqueName());
					RolePermission newRolePermission = new RolePermission();
					newRolePermission.setRoleId(newRole.getRootId());
					newRolePermission.setPermissionId(permission.getRootId());
					rolePermissionRepository.save(newRolePermission);
				});
			});
		}
		return tenantRoles;
	}

}