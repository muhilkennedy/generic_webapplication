package com.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Role;

/**
 * @author Muhil
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
	final String findRoleByUniqueNameQuery = "select r from Role r where r.roleName=:roleName";
	
	@Query(findRoleByUniqueNameQuery)
	Role findRoleByUniqueName(@Param("roleName") String roleName);

}
