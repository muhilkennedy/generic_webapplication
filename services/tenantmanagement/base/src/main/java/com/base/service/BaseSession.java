package com.base.service;

import java.util.Locale;
import java.util.TimeZone;

import com.base.entity.BaseObject;

/**
 * @author Muhil
 * 
 */
public interface BaseSession {
	
	BaseObject getTenantInfo();

	void setTenantInfo(BaseObject tenantInfo);

	void clear();

	BaseObject getUserInfo();

	void setUserInfo(BaseObject userInfo);

	String getTenantId();

	void setTenantId(String tenantId);

	Locale getLocale();

	void setLocale(String locale);
	
	void setTimeZone(String zoneId);
	
	TimeZone getTimeZone();

}
