package com.tenant.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.base.entity.BaseEntity;
import com.tenant.dao.TenantDaoService;
import com.tenant.entity.Tenant;
import com.tenant.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantDaoService tenantDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return tenantDao.findById(rootId);
	}

	@Override
	public Tenant findByUniqueName(String uniqueName) {
		Tenant tenant = tenantDao.findByUniqueName(uniqueName);
		return tenant;
	}
	
	
}
