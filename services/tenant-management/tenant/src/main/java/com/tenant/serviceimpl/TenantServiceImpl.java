package com.tenant.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Filter;
import org.hibernate.Session;
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
import com.base.util.DatabaseUtil;
import com.base.util.CacheUtil;
import com.base.util.ResultSetMapper;
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.api.model.TenantSubscriptionModel;
import com.tenant.api.model.TenantsResponse;
import com.tenant.dao.TenantDetailsRepository;
import com.tenant.dao.TenantOriginRepository;
import com.tenant.dao.TenantRepository;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;
import com.tenant.service.TenantService;
import com.tenant.service.TenantSubscriptionService;
import com.tenant.util.TenantMessageKeys;

/**
 * @author Muhil Kennedy Tenant entity related operations
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
	private TenantSubscriptionService subscriptionService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private TenantEmailService emailService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void reEvaluateTenantSession() {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("tenantFilter");
		filter.setParameter("tenantId", baseSession.getTenantId());
	}
	
	@Override
	public void reEvaluateSessionForTenant(String tenantUniqueName) {
		Tenant expectedTenant = this.findTenantByUniqueName(tenantUniqueName);
		baseSession.setTenantInfo(expectedTenant);
		reEvaluateTenantSession();
	}

	@Override
	@CachePut(value = CacheUtil.TENANT_CACHE, keyGenerator = CacheUtil.REDIS_KEY_GENERATOR)
	public Object save(BaseObject obj) {
		evictTenantByUniqueName(obj);
		return tenantRepo.save((Tenant) obj);
	}

	@Override
	@CachePut(value = CacheUtil.TENANT_CACHE, keyGenerator = CacheUtil.REDIS_KEY_GENERATOR)
	public Object saveAndFlush(BaseObject obj) {
		evictTenantByUniqueName(obj);
		return tenantRepo.saveAndFlush((Tenant) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.TENANT_CACHE, keyGenerator = CacheUtil.REDIS_KEY_GENERATOR)
	public Object findById(Object rootId) {
		return tenantRepo.findById((String) rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.TENANT_CACHE, keyGenerator = CacheUtil.REDIS_KEY_GENERATOR)
	public void delete(BaseObject obj) {
		tenantRepo.delete((Tenant) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.TENANT_CACHE, keyGenerator = CacheUtil.REDIS_KEY_GENERATOR, unless = "#result==null")
	public Tenant findTenantByUniqueName(String uniqueName) {
		return tenantRepo.findTenantByUniqueName(uniqueName);
	}

	private void evictTenantByUniqueName(BaseObject obj) {
		cacheManager.getCache(CacheUtil.TENANT_CACHE).evictIfPresent(((Tenant) obj).getTenantUniqueName());
		//cacheManager.getCache(CacheUtil.TENANT_CACHE).evictIfPresent(obj.getObjectId());
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
		TenantDetails newTenantDetails = new TenantDetails();
		newTenantDetails.setTenantEmail(tenantModel.getTenantEmail());
		newTenantDetails.setTenantContact(tenantModel.getTenantContact());
		newTenantDetails.setTagLine(tenantModel.getTagLine());
		tenantDetailsRespo.saveAndFlush(newTenantDetails);
		newTenant.setTenantDetail(newTenantDetails);
		SubscriptionHistory newSubscription = new SubscriptionHistory();
		if (tenantModel.getTenantSubscription() != null) {
			newSubscription.setExpiry(tenantModel.getTenantSubscription().fetchExpiryDate());
			newSubscription.setRenewedOn(tenantModel.getTenantSubscription().fetchRenewalDate());
			subscriptionService.saveAndFlush(newSubscription);
		}
		this.save(newTenant);
		emailService.sendOnbardingMail(newTenant, newSubscription);
		return newTenant;
	}

	@Override
	@Loggable(message = "toggle tenant status based on session", perf = true)
	public void toggleTenantStatus() {
		Tenant tenant = (Tenant) baseSession.getTenantInfo();
		tenant.setActive(!tenant.isActive());
		this.saveAndFlush(tenant);
	}

	@Override
	@Loggable(message = "toggle tenant status based on uniquename", perf = true)
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
	@Loggable(message = "adding tenant details", perf = true)
	public Tenant addTenantDetails(TenantDetailsBody tenantDetails) {
		Tenant tenant = (Tenant) baseSession.getTenantInfo();
		TenantDetails newTenantDetails = new TenantDetails();
		newTenantDetails.setTenantEmail(tenantDetails.getTenantEmail());
		newTenantDetails.setTenantContact(tenantDetails.getTenantContact());
		newTenantDetails.setTenantCity(tenantDetails.getTenantCity());
		newTenantDetails.setTenantStreet(tenantDetails.getTenantStreet());
		newTenantDetails.setTenantPin(tenantDetails.getTenantPin());
		tenantDetailsRespo.saveAndFlush(newTenantDetails);
		tenant.setTenantDetail(newTenantDetails);
		this.saveAndFlush(tenant);
		return tenant;
	}

	@Override
	public List<SubscriptionHistory> getTenantHistory() {
		return subscriptionService.findAllTenantSubscriptions(); 
	}

	@Override
	public void updateTenantExpiry(TenantSubscriptionModel subscriptionModel) {
		SubscriptionHistory activeSubscription = subscriptionService.findActiveTenantSubscription();
		if (activeSubscription != null) {
			activeSubscription.setActive(false);
			subscriptionService.save(activeSubscription);
		}
		SubscriptionHistory newSubscription = new SubscriptionHistory();
		newSubscription.setExpiry((subscriptionModel.fetchExpiryDate()));
		newSubscription.setRenewedOn((subscriptionModel.fetchRenewalDate()));
		subscriptionService.saveAndFlush(newSubscription);
	}

	@Override
	public List<TenantsResponse> getAllTenants() throws SQLException, Exception {
		List<TenantsResponse> tenantsResponse = new ArrayList<TenantsResponse>();
		List<Tenant> tenants = new ArrayList<Tenant>();
		try (Connection con = DatabaseUtil.getConnectionInstance()) {
			PreparedStatement stmt = con.prepareStatement("select * from tenant order by timecreated");
			ResultSet rs = stmt.executeQuery();
			ResultSetMapper<Tenant> mapper = new ResultSetMapper<Tenant>();
			tenants = mapper.mapRersultSetToObject(rs, Tenant.class);
			for (Tenant tenant : tenants) {
				TenantsResponse tenantResponse = new TenantsResponse();
				tenantResponse.setTenant(tenant);
				stmt = con.prepareStatement("select * from subscriptionhistory where tenantId=? and active=1 order by timecreated");
				stmt.setString(1, tenant.getRootId());
				rs = stmt.executeQuery();
				List<SubscriptionHistory> subsHistory = new ArrayList<SubscriptionHistory>();
				while (rs.next()) {
					SubscriptionHistory history = new SubscriptionHistory();
					history.setActive(rs.getBoolean("active"));
					history.setRenewedOn(rs.getDate("renewedOn"));
					history.setExpiry(rs.getDate("expiry"));
					history.setTenantId(rs.getString("tenantId"));
					subsHistory.add(history);
				}
				tenantResponse.setSubscriptions(subsHistory);
				tenantsResponse.add(tenantResponse);
			}
		}
		return tenantsResponse;
	}

}
