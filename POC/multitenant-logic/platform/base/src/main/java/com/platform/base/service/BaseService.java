package com.platform.base.service;

/**
 * @author Muhil
 * 
 */
public interface BaseService {
	Object getTenantInfo();

	void setTenantInfo(Object tenantInfo);

	void clear();

	Object getUserInfo();

	void setUserInfo(Object userInfo);

	void setTenantId(String tenantId);

	String getTenantId();

}
