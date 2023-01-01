package com.tenant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tenant.entity.SubscriptionHistory;

/**
 * @author Muhil
 *
 */
@Repository
public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, String> {

	final String findSubscriptionsForTenantQuery = "select t from SubscriptionHistory t";
	
	@Query(findSubscriptionsForTenantQuery)
	List<SubscriptionHistory> findSubscriptionsForTenant();
	
	final String findActiveSubscriptionForTenantQuery = "select t from SubscriptionHistory t where active=1 and tenantId=:tenantId";
	
	@Query(findActiveSubscriptionForTenantQuery)
	SubscriptionHistory findActiveSubscriptionForTenant(@Param("tenantId") String tenantId);
	
}
