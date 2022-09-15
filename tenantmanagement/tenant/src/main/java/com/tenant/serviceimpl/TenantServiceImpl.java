package com.tenant.serviceimpl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.entity.BaseObject;
import com.base.util.RedisCacheUtil;
import com.tenant.api.model.TenantModel;
import com.tenant.dao.TenantDetailsRepository;
import com.tenant.dao.TenantOriginRepository;
import com.tenant.dao.TenantRepository;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;
import com.tenant.service.TenantService;

/**
 * @author Muhil Kennedy Tenant entity related operations
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {

	@Autowired
	private TenantRepository tenantRepo;

	@Autowired
	private TenantDetailsRepository tenantDetailsRespo;

	@Autowired
	private TenantOriginRepository tenantOriginRepo;

	@Override
	@CachePut(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Object save(BaseObject obj) {
		return tenantRepo.save((Tenant) obj);
	}

	@Override
	@CachePut(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Object saveAndFlush(BaseObject obj) {
		return tenantRepo.saveAndFlush((Tenant) obj);
	}

	@Override
	@Cacheable(value = RedisCacheUtil.TENANT_CACHE)
	public Optional<Tenant> findById(Object rootId) {
		return tenantRepo.findById((String) rootId);
	}
	
	@Override
	@CacheEvict(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public void delete(BaseObject obj) {
		tenantRepo.delete((Tenant)obj);
	}
	
	@Cacheable(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Tenant findTenantByUniqueName(String uniqueName) {
		return tenantRepo.findTenantByUniqueName(uniqueName);
	}

	@Override
	public TenantDetails addTenantDetail(TenantDetails tenantDetail) {
		return tenantDetailsRespo.saveAndFlush(tenantDetail);
	}

	@Override
	public TenantOrigin addTenantOrigin(TenantOrigin tenantOrigin) {
		return tenantOriginRepo.saveAndFlush(tenantOrigin);
	}

	@Override
	public Tenant createTenant(TenantModel tenantModel) {
		Tenant newTenant = (Tenant) this.saveAndFlush(tenantModel.getTenant());
		TenantDetails tenantDetails = this.tenantDetailsRespo.saveAndFlush(tenantModel.getTenantDetails());
		newTenant.setTenantDetail(tenantDetails);
		TenantOrigin tenantOrigin = this.tenantOriginRepo.saveAndFlush(tenantModel.getTenantOrigin());
		newTenant.setTenantOrigin(Arrays.asList(tenantOrigin));
		return newTenant;
	}

}
