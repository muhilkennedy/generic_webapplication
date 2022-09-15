package com.base.util;

/**
 * @author Muhil Kennedy
 *
 */
public class Constants {
	
	public static enum DEPLOYMENT_MODES {
		prod, dev
	}
	
	public static String BASE_PACKAGE = "com.base*";
	public static String TENANT_PACKAGE = "com.tenant*";
	public static String CORE_PACKAGE = "com.core*";
	
	public static final String Key_Hibernate_Interceptor = "hibernate.ejb.interceptor";
	public static final String TENANT_FILTER_NAME = "tenantFilter";
	public static final String TENANT_PARAMETER_NAME = "tenantId";
	public static final String TENANT_PARAMETER_TYPE = "string";
	public static final String TENANT_COLUMN_NAME = "tenantId";
	
	public static final String TENANT_HEADER = "X-Tenant";
	public static final String TENANTID_HEADER = "X-TenantId";
	
	public static final String COLON_SEPERATOR = ":";

}
