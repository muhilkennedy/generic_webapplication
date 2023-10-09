package com.platform.util;

import java.text.SimpleDateFormat;

/**
 * @author Muhil
 *
 */
public class PlatformUtil {

	public static final String EMPTY_STRING = "";
	
	public static final String DEFAULT_USER_ID = "SYSTEM";
	public static final Long SYSTEM_USER_ROOTID= 0L;
	
	public static final String ADMIN_CUSTOMER_SUPPORT_DESIGNATION = "CustomerSupportAdmin";
	
	public static final String TENANT_HEADER = "X-Tenant";
	public static final String TENANTID_HEADER = "X-TenantId";
	public static final String TOKEN_HEADER = "X-Token";
	
	public static final String TENANT_PARAM = "tenantId";
	
	public static final String COLON_SEPERATOR = ":";
	
	public static final SimpleDateFormat SIMPLE_DATE_ONLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

}
