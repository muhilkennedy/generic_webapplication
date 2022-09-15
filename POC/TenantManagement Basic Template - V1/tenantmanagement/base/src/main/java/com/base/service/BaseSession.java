package com.base.service;

/**
 * @author Muhil
 * 
 */
public interface BaseSession {
	Object getTenantInfo();

	void setTenantInfo(Object tenantInfo);

	void clear();

	Object getUserInfo();

	void setUserInfo(Object userInfo);

	String getTenantId();

	void setTenantId(String tenantId);

}
