package com.tenant.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.service.StorageService;
import com.platform.util.ImageUtil;
import com.platform.util.PlatformUtil;
import com.tenant.dao.TenantDaoService;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantSubscription;
import com.tenant.messages.TenantRequestBody;
import com.tenant.model.TenantInfo;
import com.tenant.service.TenantService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author muhil
 */
@Service
@Qualifier("TenantService")
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantDaoService tenantDao;
	
	@Autowired
	private TenantEmailService emailService;

	@Override
	public BaseEntity findById(Long rootId) {
		return tenantDao.findById(rootId);
	}

	@Override
	public Tenant findByUniqueName(String uniqueName) {
		Tenant tenant = tenantDao.findByUniqueName(uniqueName);
		return tenant;
	}

	@Override
	public List<Tenant> findAllTenants() {
		return tenantDao.findAll();
	}
	
	@Override
	public Tenant createTenant(TenantRequestBody tenantModel) {
		Tenant newTenant = new Tenant();
		newTenant.setName(tenantModel.getTenantName());
		newTenant.setUniquename(tenantModel.getUniqueName());
		newTenant.setParent(tenantModel.getParentTenant());
		tenantDao.saveAndFlush(newTenant);
		// reset base session to new thread context.
		BaseSession.setTenant(newTenant);
		TenantDetails newTenantDetails = new TenantDetails(newTenant);
		newTenantDetails.setBusinessemail(tenantModel.getBusinessEmail());
		newTenantDetails.setBusinessemailpassword(tenantModel.getBusinessPassword());
		newTenantDetails.setTagline(tenantModel.getTagLine());
		newTenantDetails.setContact(tenantModel.getContact());
		newTenantDetails.setCity(tenantModel.getCity());
		newTenantDetails.setStreet(tenantModel.getStreet());
		newTenantDetails.setEmailid(tenantModel.getEmail());
		TenantInfo tenantInfo = new TenantInfo();
		tenantInfo.setGmapUrl(tenantModel.getDetails().getGmapUrl());
		tenantInfo.setFssai(tenantModel.getDetails().getFssai());
		tenantInfo.setGstin(tenantModel.getDetails().getGstin());
		newTenantDetails.setDetails(tenantInfo);
		newTenant.setTenantDetail(newTenantDetails);
		tenantDao.saveAndFlush(newTenant);
		TenantSubscription newSubscription = addTenantSubscription(tenantModel);
		//Notify tenant
		emailService.sendOnbardingMail(newTenant, newSubscription);
		//Deactivate now to enable later based on effective date
		toggleTenantState(newTenant.getUniquename());
		return newTenant;
	}
	
	@Override
	public TenantSubscription addTenantSubscription(TenantRequestBody tenantModel) {
		TenantSubscription newSubscription = new TenantSubscription();
		newSubscription.setStartdate(tenantModel.getStartDate());
		newSubscription.setEnddate(tenantModel.getEndDate());
		return tenantDao.saveTenantSubscription(newSubscription);
	}

	@Override
	public Tenant uploadLogo(File logo) throws IOException {
		Tenant tenant = (Tenant) BaseSession.getTenant();
		tenant.getTenantDetail().getDetails().setLogoUrl(StorageService.getStorage().saveFile(logo));
		// now compress and store thumbnail image
		tenant.getTenantDetail().getDetails()
				.setLogoThumbnail(StorageService.getStorage().saveFile(ImageUtil.getPNGThumbnailImage(logo)));
		return (Tenant) tenantDao.save(tenant);
	}

	@Override
	public void toggleTenantState(String uniqueName) {
		BaseEntity tenant = findByUniqueName(uniqueName);
		Assert.notNull(tenant, "Requested tenant not found");
		tenant.setActive(!tenant.isActive());
		tenantDao.save(tenant);
	}
	
	@Override
	public Flux<TenantSubscription> getAllTenantSubscription(Long tenantId){
		return tenantDao.getAllTenantSubscription(tenantId);
	}
	
	@Override
	public TenantSubscription getActiveTenantSubscription(Long tenantId){
		return tenantDao.getActiveTenantSubscription(tenantId).block();
	}
	
	@Override
	public void checkAndRenewTenant() {
		List<TenantSubscription> subs = tenantDao.getAllTenantSubscription(BaseSession.getTenantId()).collectList()
				.block();
		for (TenantSubscription sub : subs) {
			Instant currentInstant = Instant.now();
			Instant subscriptionStartDate = Instant.ofEpochMilli(sub.getStartdate().getTime());
			Log.tenant.debug("checkAndRenewTenant : {} : subscriptionStartDate {}", BaseSession.getTenantUniqueName(),
					subscriptionStartDate);
			if (!sub.isActive() && currentInstant.isAfter(subscriptionStartDate)) {
				sub.setActive(true);
				tenantDao.saveTenantSubscription(sub);
			} else if (sub.isActive() && currentInstant.isBefore(subscriptionStartDate)) {
				sub.setActive(false);
				tenantDao.saveTenantSubscription(sub);
			}
		}
		TenantSubscription activeSubscription = getActiveTenantSubscription(BaseSession.getTenantId());
		if ((activeSubscription != null && !BaseSession.getTenant().isActive())
				|| (activeSubscription == null && BaseSession.getTenant().isActive())) {
			toggleTenantState(BaseSession.getTenantUniqueName());
		}
	}
	
}
