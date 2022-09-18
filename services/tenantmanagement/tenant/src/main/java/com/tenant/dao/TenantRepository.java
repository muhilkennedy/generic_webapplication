package com.tenant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.SequenceNumber;
import com.tenant.entity.Tenant;

/**
 * @author Muhil
 *
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {
	
	final String findTenantByUniquename = "select t from Tenant t where t.tenantUniqueName = :uniqueName";
	
	@Query(findTenantByUniquename)
	Tenant findTenantByUniqueName(@Param("uniqueName") String uniqueName);

}
