package com.base.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Muhil Kennedy
 *
 */
@Component
public class PropertiesUtil {

	private static String activeProfile;

	@Value("${spring.profiles.active}")
	public void setActiveProfile(String profile) {
		PropertiesUtil.activeProfile = profile;
	}

	public static String getActiveProfile() {
		return activeProfile;
	}

	public static boolean isProdDeployment() {
		if (activeProfile.equalsIgnoreCase(Constants.DEPLOYMENT_MODES.prod.name())) {
			return true;
		}
		return false;
	}

}
