package com.tenant.serviceimpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.base.annotation.Loggable;
import com.base.entity.BaseObject;
import com.base.service.BaseSession;
import com.base.util.RedisCacheUtil;
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.dao.TenantDetailsRepository;
import com.tenant.dao.TenantOriginRepository;
import com.tenant.dao.TenantRepository;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;
import com.tenant.service.TenantService;
import com.tenant.util.TenantMessageKeys;

/**
 * @author Muhil Kennedy 
 * Tenant entity related operations
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private BaseSession baseSession;

	@Autowired
	private TenantRepository tenantRepo;

	@Autowired
	private TenantDetailsRepository tenantDetailsRespo;

	@Autowired
	private TenantOriginRepository tenantOriginRepo;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CacheManager cacheManager;

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
	@Cacheable(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Optional<Tenant> findById(Object rootId) {
		return tenantRepo.findById((String) rootId);
	}
	
	@Override
	@CacheEvict(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public void delete(BaseObject obj) {
		tenantRepo.delete((Tenant)obj);
	}
	
	@Override
	@Cacheable(value = RedisCacheUtil.TENANT_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR, unless="#result==null")
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
	@Loggable(message = "create tenant execution", perf = true)
	public Tenant createTenant(TenantRequestBody tenantModel) {
		Tenant newTenant = new Tenant();
		newTenant.setTenantName(tenantModel.getTenantName());
		newTenant.setTenantUniqueName(tenantModel.getTenantUniqueName());
		this.saveAndFlush(newTenant);
		baseSession.setTenantInfo(newTenant);
		return newTenant;
	}

	@Override
	public void toggleTenantStatus() {
		Tenant tenant = (Tenant) baseSession.getTenantInfo();
		tenant.setActive(!tenant.isActive());
		this.saveAndFlush(tenant);
	}

	@Override
	public void toggleTenantStatus(String tenantUniqueName) throws TenantException {
		Tenant tenant = this.findTenantByUniqueName(tenantUniqueName);
		if (tenant == null) {
			throw new TenantException(messageSource.getMessage(TenantMessageKeys.INVALID.getKey(),
					new String[] { tenantUniqueName }, baseSession.getLocale()));
		}
		tenant.setActive(!tenant.isActive());
		this.saveAndFlush(tenant);
	}
	
	@Override
	public Tenant addTenantDetails(TenantDetailsBody tenantDetails) {
		Tenant tenant = (Tenant) baseSession.getTenantInfo();
		cacheManager.getCache(RedisCacheUtil.TENANT_CACHE).evictIfPresent(tenant.getTenantUniqueName());
		TenantDetails newTenantDetails = new TenantDetails();
		newTenantDetails.setTenantEmail(tenantDetails.getTenantEmail());
		newTenantDetails.setTenantContact(tenantDetails.getTenantContact());
		newTenantDetails.setBusinessEmail(tenantDetails.getBusinessEmail());
		newTenantDetails.setBusinessEmailPassword(tenantDetails.getBusinessEmailPassword());
		newTenantDetails.setTenantCity(tenantDetails.getTenantCity());
		newTenantDetails.setTenantStreet(tenantDetails.getTenantStreet());
		newTenantDetails.setTenantPin(tenantDetails.getTenantPin());
		tenantDetailsRespo.saveAndFlush(newTenantDetails);
		tenant.setTenantDetail(newTenantDetails);
		this.saveAndFlush(tenant);
		return tenant;
	}

}
