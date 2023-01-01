package com.user.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.base.security.Permissions;
import com.base.util.Log;
import com.user.dao.PermissionRepository;
import com.user.entity.Permission;

/**
 * @author Muhil
 *
 */
@Configuration
public class RolesAndPermissionsConfiguration {

	@Autowired
	private PermissionRepository permissionRepo;

	@PostConstruct
	private void loadRolesAndPermissionsOnServerStartup() {
		List<Permission> permissionsList = permissionRepo.findAll();
		Permissions.stream().forEach(enumPermission -> {
			if (!permissionsList.parallelStream()
					.filter(perm -> perm.getPermission().equals(enumPermission.getPermissionUniqueName())).findAny()
					.isPresent()) {
				Permission newPermission = new Permission();
				newPermission.setActive(true);
				newPermission.setPermission(enumPermission.getPermissionUniqueName());
				permissionRepo.saveAndFlush(newPermission);
				Log.base.info("New Permission Loaded : " + newPermission.getRootId());
			}
		});
		// Load default roles for tenant if not present
		//List<Permission> refreshedPermissionsList = permissionRepo.findAll();
	}

}
