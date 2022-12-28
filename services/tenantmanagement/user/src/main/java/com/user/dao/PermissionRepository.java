package com.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Permission;
import com.user.entity.Role;

/**
 * @author Muhil
 *
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	
	final String findPermissionByUniqueNameQuery = "select p from Permission p where p.permission=:permissionName";
	
	@Query(findPermissionByUniqueNameQuery)
	Permission findPermissionByUniqueName(@Param("permissionName") String permissionName);

}
