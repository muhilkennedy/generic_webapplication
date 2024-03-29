package com.tenant.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.base.service.BaseReactiveDaoService;
import com.base.util.CacheUtil;
import com.platform.util.PlatformUtil;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.jpa.repository.TenantRepository;
import com.tenant.jpa.repository.TenantSubscriptionRepository;
import com.tenant.reactive.repository.TenantR2Repository;
import com.tenant.reactive.repository.TenantSubscriptionR2Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("TenantDao")
public class TenantDaoService implements BaseDaoService, BaseReactiveDaoService {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private TenantR2Repository tenantR2Repository;
	
	@Autowired
	private TenantSubscriptionRepository subscriptionRepository;
	
	@Autowired
	private TenantSubscriptionR2Repository subscriptionR2Repository;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public Flux<?> findAllReactive() {
		return tenantR2Repository.findAll();
	}

	@Override
	public Flux<?> saveAll(List<?> entities) {
		// TODO
		return null;
	}

	@Override
	public void deleteAll(List<?> entities) {
		tenantRepository.deleteAll((Iterable<Tenant>) entities);
	}

	@Override
	@CachePut(value = CacheUtil.TENANT_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity save(BaseEntity obj) {
		Tenant tenant = (Tenant) obj;
		evictTenantByUniqueName(tenant.getUniquename());
		return tenantRepository.save(tenant);
	}

	@Override
	@CachePut(value = CacheUtil.TENANT_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		Tenant tenant = (Tenant) obj;
		evictTenantByUniqueName(tenant.getUniquename());
		return tenantRepository.saveAndFlush(tenant);
	}

	@Override
	@Cacheable(value = CacheUtil.TENANT_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return tenantRepository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.TENANT_CACHE_NAME, key = "#obj.rootId")
	public void delete(BaseEntity obj) {
		tenantRepository.delete((Tenant) obj);
	}

	@Override
	public List<Tenant> findAll() {
		return tenantRepository.findAll();
	}

	@Override
	@CacheEvict(value = CacheUtil.TENANT_CACHE_NAME, key = "#rootId")
	public void deleteById(Long rootId) {
		tenantRepository.deleteById(rootId);
	}

	@Cacheable(value = CacheUtil.TENANT_CACHE_NAME, key = "#uniqueName")
	public Tenant findByUniqueName(String uniqueName) {
		return tenantRepository.findTenantByUniqueName(uniqueName);
	}

	private void evictTenantByUniqueName(String uniqueName) {
		cacheManager.getCache(CacheUtil.TENANT_CACHE_NAME).evictIfPresent(uniqueName);
	}
	
	public TenantSubscription saveTenantSubscription(TenantSubscription sub) {
		return subscriptionRepository.save(sub);
	}
	
	public Flux<TenantSubscription> getAllTenantSubscription(Long tenantId){
		return subscriptionR2Repository.findllTenantSubscription(tenantId);
	}
	
	public Mono<TenantSubscription> getActiveTenantSubscription(Long tenantId){
		return subscriptionR2Repository.findActiveTenantSubscription(tenantId);
	}
	
	public List<Long> findTenantsToActivate() {
		Date date = new Date();
		String dt = PlatformUtil.SIMPLE_DATE_ONLY_FORMAT.format(date.getTime());
		return subscriptionRepository.findTS(dt);
	}

	public List<Long> findTenantsToDeactivate() {
		Date date = new Date();
		String dt = PlatformUtil.SIMPLE_DATE_ONLY_FORMAT.format(date.getTime());
		return subscriptionRepository.findExpireTS(dt);
	}


}
