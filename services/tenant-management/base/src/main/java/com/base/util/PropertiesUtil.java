package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Muhil Kennedy
 *
 */
@Component
public class PropertiesUtil {

	private static String activeProfile;
	private static String defaultDirectory;

	@Value("${spring.profiles.active}")
	public void setActiveProfile(String profile) {
		PropertiesUtil.activeProfile = profile;
	}

	@Value("${app.nfs.path}")
	public void setDefaultDirectoryProfile(String defaultDirectory) {
		PropertiesUtil.defaultDirectory = defaultDirectory;
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
		return EnvPropertiesUtil.getEnvironmentValue("DB_SECRET");
	}
	
	public static String getJWTSecret() {
		return EnvPropertiesUtil.getEnvironmentValue("JWT_SECRET");
	}

	public static String getDefaultDirectory() {
		return System.getProperty("default.dir");
	}
	
	public static String getFileEncryptionSecret() {
		return EnvPropertiesUtil.getEnvironmentValue("FILE_SECRET");
	}

}
