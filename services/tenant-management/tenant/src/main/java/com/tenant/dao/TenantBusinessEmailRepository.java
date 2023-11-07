package com.tenant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tenant.entity.TenantBusinessEmail;

/**
 * @author Muhil
 *
 */
public interface TenantBusinessEmailRepository extends JpaRepository<TenantBusinessEmail, String> {

	final String findTenantBusinessEmailQuery = "select t from TenantBusinessEmail t";

	@Query(findTenantBusinessEmailQuery)
	List<TenantBusinessEmail> findTenantBusinessEmail();

	final String findActiveTenantBusinessEmailQuery = "select t from TenantBusinessEmail t where active=1 and tenantId=:tenantId";

	@Query(findActiveTenantBusinessEmailQuery)
	TenantBusinessEmail findActiveTenantBusinessEmailQuery(@Param("tenantId") String tenantId);

}
