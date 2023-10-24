package com.mken.base.session;

import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import com.mken.base.entity.BaseEntity;

/**
 * @author Muhil
 * Basesession should be set for each http request/thread.
 */
public class BaseSession {

	private static final ThreadLocal<BaseEntity> user = new ThreadLocal<>();
	private static final ThreadLocal<Locale> locale = new ThreadLocal<>();
	
	public static void setUser(BaseEntity usr) {
		user.set(usr);
	}

	public static BaseEntity getUser() {
		return user.get();
	}
	
	public static void setLocale(Locale loc) {
		locale.set(loc);
	}
	
	public static void setLocale(String localeCode) {
		locale.set(LocaleUtils.toLocale(localeCode));
	}
	
	public static Locale getLocale() {
		return locale.get();
	}
	
	public static void clear() {
		user.remove();
		locale.remove();
	}

}
