package com.base.serviceimpl;

import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;

import com.base.entity.BaseObject;
import com.base.service.BaseSession;
import com.base.util.Log;

/**
 * @author Muhil
 * Threadlocal impl to handle tenant/user info for each spring request
 */
@Service
public class BaseSessionImpl implements BaseSession {

	private ThreadLocal<BaseObject> tenantInfo = new ThreadLocal<BaseObject>();
	private ThreadLocal<BaseObject> userInfo = new ThreadLocal<BaseObject>();
	private ThreadLocal<String> tenantId = new ThreadLocal<String>();
	private ThreadLocal<Locale> locale = new ThreadLocal<Locale>();
	private ThreadLocal<TimeZone> timeZone = new ThreadLocal<TimeZone>();

	@Override
	public BaseObject getTenantInfo() {
		return tenantInfo.get();
	}

	@Override
	public void setTenantInfo(BaseObject tenantInfo) {
		this.tenantInfo.set(tenantInfo);
		this.tenantId.set(tenantInfo.getObjectId());
	}

	@Override
	public BaseObject getUserInfo() {
		return userInfo.get();
	}

	@Override
	public void setUserInfo(BaseObject userInfo) {
		this.userInfo.set(userInfo);
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId.set(tenantId);
	}

	@Override
	public String getTenantId() {
		return tenantId.get();
	}

	@Override
	public Locale getLocale() {
		return locale.get();
	}

	@Override
	public void setLocale(String localeCode) {
		this.locale.set(LocaleUtils.toLocale(localeCode));
	}
	
	@Override
	public void setTimeZone(String zoneId) {
		TimeZone timeZone = TimeZone.getTimeZone(zoneId);
		if (timeZone == null) {
			Log.base.warn("Rolling back to server time zone for tenant : " + tenantId.get());
			timeZone = TimeZone.getDefault();
		}
		this.timeZone.set(timeZone);
	}

	@Override
	public TimeZone getTimeZone() {
		return timeZone.get();
	}
	
	@Override
	public void clear() {
		tenantInfo.remove();
		userInfo.remove();
		tenantId.remove();
		locale.remove();
		timeZone.remove();
	}
	
}