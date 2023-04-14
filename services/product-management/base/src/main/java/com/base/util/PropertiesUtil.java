package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import com.platform.util.EnvPropertiesUtil;

/**
 * @author Muhil
 *
 */
public class PropertiesUtil {

	private static Environment env;
	private static String activeProfile;
	private static String defaultDirectory;

	public static void setEnvironment(Environment env) {
		PropertiesUtil.env = env;
	}

	public static String getProperty(String key) {
		return env.getProperty(key);
	}

	public static boolean getBooleanProperty(String key) {
		return env.getProperty(key, Boolean.class);
	}

	public static int getIntProperty(String key) {
		return env.getProperty(key, Integer.class);
	}
	
	public static String getEnvironmentValue(final String key) {
		String value = System.getenv(key);
		Assert.isTrue(StringUtils.isAllBlank(value), "ENVIROMENT_VARIABLE_" + key + "_NOT_DEFNINED");
		return value;
	}
	
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
		String key = env.getProperty("encryption.db.secret");
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("DB_SECRET");
	}
	
	public static String getJWTSecret() {
		String key = env.getProperty("encryption.jwt.secret");
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("JWT_SECRET");
	}
	
	public static String getFileSecret() {
		String key = env.getProperty("encryption.file.secret");
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("FILE_SECRET");
	}

	public static String getDefaultDirectory() {
		String dir = System.getProperty("default.dir");
		if (StringUtils.isAllBlank(dir)) {
			return defaultDirectory;
		}
		return dir;
	}


}
