package com.tenant.service;

import java.util.List;

import com.base.service.BaseService;
import com.tenant.entity.SubscriptionHistory;

public interface TenantSubscriptionService extends BaseService {
	
	List<SubscriptionHistory> findAllTenantSubscriptions();
	
	SubscriptionHistory findActiveTenantSubscription();

}
