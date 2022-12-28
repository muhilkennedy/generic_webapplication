package com.tenant.api.model;

import java.util.List;

import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;

public class TenantsResponse {

	private Tenant tenant;
	private List<SubscriptionHistory> subscriptions;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public List<SubscriptionHistory> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<SubscriptionHistory> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
