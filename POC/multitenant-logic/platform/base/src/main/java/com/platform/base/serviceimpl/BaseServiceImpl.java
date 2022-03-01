package com.platform.base.serviceimpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.platform.base.service.BaseService;

/**
 * @author Muhil
 *
 */
@Component
@Service
public class BaseServiceImpl implements BaseService {

	private ThreadLocal<Object> tenantInfo = new ThreadLocal<Object>();
	private ThreadLocal<Object> userInfo = new ThreadLocal<Object>();
	private ThreadLocal<String> tenantId = new ThreadLocal<String>();

	@Override
	public Object getTenantInfo() {
		return tenantInfo.get();
	}

	@Override
	public void setTenantInfo(Object tenantInfo) {
		this.tenantInfo.set(tenantInfo);
	}

	@Override
	public Object getUserInfo() {
		return userInfo;
	}

	@Override
	public void setUserInfo(Object userInfo) {
		this.userInfo.set(userInfo);
	}
	
	@Override
	public void clear() {
		setTenantInfo(null);
		setUserInfo(null);
	}

	@Override
	public void setTenantId(String tenantId) {
		// TODO Auto-generated method stub
		this.tenantId.set(tenantId);
	}

	@Override
	public String getTenantId() {
		// TODO Auto-generated method stub
		return tenantId.get();
	}
	
}