package com.platform.util;

import java.util.Arrays;
import java.util.List;

import com.platform.configuration.PlatformConfiguration;

/**
 * @author Muhil
 *
 */
public class PropertiesUtil {
	
	public static List<String> getMandatoryProperties() {
		return Arrays.asList(PlatformUtil.KEY_TENANT_FRONTDOOR, PlatformUtil.KEY_USER_FRONTDOOR);
	}

	public static String getTMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(PlatformUtil.KEY_TENANT_FRONTDOOR);
	}

	public static String getUMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(PlatformUtil.KEY_USER_FRONTDOOR);
	}

}
