package com.tenant.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.base.service.BaseReactiveDaoService;
import com.tenant.entity.Tenant;
import com.tenant.reactive.repository.TenantR2Repository;
import com.tenant.repository.jpa.TenantRepository;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("TenantDao")
public class TenantDaoService implements BaseDaoService, BaseReactiveDaoService  {
	
	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private TenantR2Repository tenantR2Repository;

	@Override
	public Flux<?> findAllReactive() {
		return tenantR2Repository.findAll();
	}

	@Override
	public Flux<?> saveAll(List<?> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(List<?> entities) {
		
	}

	@Override
	public BaseEntity save(BaseEntity obj) {
		return tenantRepository.save((Tenant)obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return tenantRepository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long rootId) {
		// TODO Auto-generated method stub
		
	}
	
	public Tenant findByUniqueName(String uniqueName) {
		return tenantRepository.findTenantByUniqueName(uniqueName);
	}

}
