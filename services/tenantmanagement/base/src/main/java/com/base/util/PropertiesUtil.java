package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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
	
	public static String getDBEncryptionSecret() {
		String secret = EnvPropertiesUtil.getEnvironmentValue("DB_SECRET");
		if (StringUtils.isAllBlank(secret)) {
			Log.base.warn("----- DB_SECRET is missing! consider adding the property -----");
			Log.base.info("Falling back to default secret");
			return "0123456789abcdef";
		}
		return secret;
	}

}
