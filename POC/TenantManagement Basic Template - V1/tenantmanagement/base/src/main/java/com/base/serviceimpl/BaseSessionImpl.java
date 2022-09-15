package com.base.serviceimpl;

import org.springframework.stereotype.Service;

import com.base.service.BaseSession;

/**
 * @author Muhil
 * Threadlocal impl to handle tenant/user info for each spring request
 */
@Service
public class BaseSessionImpl implements BaseSession {

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
		this.tenantId.set(tenantId);
	}

	@Override
	public String getTenantId() {
		return tenantId.get();
	}
	
}