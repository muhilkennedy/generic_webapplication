package com.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.RolePermission;

/**
 * @author Muhil
 *
 */
@Repository
public interface RolePermissionRepository  extends JpaRepository<RolePermission, String> {

}
