package com.tenant.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseObject;
import com.base.service.BaseSession;
import com.tenant.dao.SubscriptionHistoryRepository;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.service.TenantSubscriptionService;

@Service
public class TenantSubscriptionServiceImpl implements TenantSubscriptionService {
	
	@Autowired
	private BaseSession baseSession;

	@Autowired
	private SubscriptionHistoryRepository subscriptionRepository;

	@Override
	public Object save(BaseObject obj) {
		return subscriptionRepository.save((SubscriptionHistory) obj);
	}

	@Override
	public Object saveAndFlush(BaseObject obj) {
		return subscriptionRepository.saveAndFlush((SubscriptionHistory) obj);
	}

	@Override
	public SubscriptionHistory findById(Object rootId) {
		return subscriptionRepository.findById(rootId.toString()).get();
	}

	@Override
	public void delete(BaseObject obj) {
		subscriptionRepository.delete((SubscriptionHistory) obj);
	}

	@Override
	public List<SubscriptionHistory> findAllTenantSubscriptions() {
		return subscriptionRepository.findAll();
	}

	@Override
	public SubscriptionHistory findActiveTenantSubscription() {
		return subscriptionRepository.findActiveSubscriptionForTenant(baseSession.getTenantId());
	}

}
