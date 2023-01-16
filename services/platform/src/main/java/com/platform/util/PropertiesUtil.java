package com.platform.util;

import java.util.Arrays;
import java.util.List;

import com.platform.configuration.PlatformConfiguration;

/**
 * @author Muhil
 *
 */
public class PropertiesUtil {
	
	public static List<String> getMandatoryFrontdoorProperties() {
		return Arrays.asList(PlatformUtil.KEY_TENANT_FRONTDOOR, PlatformUtil.KEY_USER_FRONTDOOR);
	}

	public static List<String> getMandatoryEmailProperties() {
		return Arrays.asList(PlatformUtil.KEY_DEFAULT_EMAIL, PlatformUtil.KEY_DEFAULT_PASSWORDs,
				PlatformUtil.KEY_MAIL_AUTH, PlatformUtil.KEY_MAIL_HOST, PlatformUtil.KEY_MAIL_PORT);
	}

	public static String getTMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(PlatformUtil.KEY_TENANT_FRONTDOOR);
	}

	public static String getUMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(PlatformUtil.KEY_USER_FRONTDOOR);
	}
	
	public static String getDefaultEmail() {
		return PlatformConfiguration.getEmailProperties().getProperty(PlatformUtil.KEY_DEFAULT_EMAIL);
	}
	
	public static String getDefaultEmailPassword() {
		return PlatformConfiguration.getEmailProperties().getProperty(PlatformUtil.KEY_DEFAULT_PASSWORDs);
	}

}
