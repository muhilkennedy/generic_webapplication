package com.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.base.server.BaseSession;
import com.platform.user.Permissions;
import com.user.dao.RolePermissionDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.entity.RolePermission;
import com.user.messages.RoleRequest;
import com.user.service.EmployeeService;
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
	
	@Autowired
	private EmployeeService empService;
	
	@Override
	public List<Role> findAllRoles() {
		return rpDaoService.findAllRoles();
	}

	/**
	 * can view superuser permission only by a superuser
	 */
	@Override
	public List<Permission> findAllPermission() {
		return rpDaoService.findAllPermission().stream().filter(permission -> !(permission.getPermission()
				.equalsIgnoreCase(Permissions.SUPER_USER.name())
				&& !empService.doesEmployeeHavePermission(Permissions.SUPER_USER, BaseSession.getUser().getRootId())))
				.toList();
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
		List<Permission> permissionsList = findAllPermission().parallelStream()
				.filter(permission -> roleRequest.getPermissionIds().contains(permission.getRootid())).toList();
		List<RolePermission> rp = permissionsList.stream().map(permission -> rpDaoService
				.saveRolePermissionMap(new RolePermission(permission, role))).toList();
		role.setPermissions(rp);
		return role;
	}
	
	@Override
	public Employee addRolesToEmployee(Employee employee, List<Long> roleIds) {
		List<Role> rolesList = findAllRoles().parallelStream().filter(role -> roleIds.contains(role.getRootId()))
				.toList();
		List<EmployeeRole> er = rolesList.stream()
				.map(role -> rpDaoService.saveEmployeeRoleMap(new EmployeeRole(employee, role))).toList();
		employee.setEmployeeeRoles(er);
		return employee;
	}

}
