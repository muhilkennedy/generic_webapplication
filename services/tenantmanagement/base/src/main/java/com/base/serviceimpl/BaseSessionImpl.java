package com.base.serviceimpl;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.base.entity.BaseObject;
import com.base.service.BaseSession;

/**
 * @author Muhil
 * Threadlocal impl to handle tenant/user info for each spring request
 */
@Service
public class BaseSessionImpl implements BaseSession {

	private ThreadLocal<BaseObject> tenantInfo = new ThreadLocal<BaseObject>();
	private ThreadLocal<Object> userInfo = new ThreadLocal<Object>();
	private ThreadLocal<String> tenantId = new ThreadLocal<String>();
	private ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

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
	public Object getUserInfo() {
		return userInfo;
	}

	@Override
	public void setUserInfo(Object userInfo) {
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
	public void setLocale(String locale) {
		this.locale.set(com.i18n.util.Locale.getValidLocale(locale));
	}
	
	@Override
	public void clear() {
		tenantInfo.remove();
		userInfo.remove();
		tenantId.remove();
		locale.remove();
	}
	
}