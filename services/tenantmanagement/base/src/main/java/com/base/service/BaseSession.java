package com.base.service;

import java.util.Locale;

import com.base.entity.BaseObject;

/**
 * @author Muhil
 * 
 */
public interface BaseSession {
	
	Object getTenantInfo();

	void setTenantInfo(BaseObject tenantInfo);

	void clear();

	Object getUserInfo();

	void setUserInfo(Object userInfo);

	String getTenantId();

	void setTenantId(String tenantId);

	Locale getLocale();

	void setLocale(String locale);

}
