package com.platform.session;

import java.util.Locale;
import java.util.TimeZone;

import com.platform.entity.Tenant;
import com.platform.entity.User;

/**
 * @author Muhil
 *
 */
public class BaseSession {

	private static final ThreadLocal<Tenant> tenant = new ThreadLocal<Tenant>();
	private static final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();
	private static final ThreadLocal<TimeZone> timeZone = new ThreadLocal<TimeZone>();
	private static final ThreadLocal<User> user = new ThreadLocal<User>();

	public static Tenant getTenant() {
		return tenant.get();
	}

	public static void setTenant(Tenant tenantInfo) {
		tenant.set(tenantInfo);
	}
	
	public static String getTenantId() {
		return tenant.get().getRootId();
	}

	public static void clear() {
		tenant.remove();
		locale.remove();
		timeZone.remove();
		user.remove();
	}

	public Locale getLocale() {
		return locale.get();
	}

	public void setLocale(Locale localeInfo) {
		locale.set(localeInfo);
	}

	public TimeZone getTimeZone() {
		return timeZone.get();
	}

	public void setTimeZone(TimeZone timeZoneInfo) {
		timeZone.set(timeZoneInfo);
	}
	
	public static void setUser(User usr) {
		user.set(usr);
	}

	public static User getUser() {
		return user.get();
	}

}
