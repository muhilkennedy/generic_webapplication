package com.base.util;

import java.util.Arrays;
import java.util.Properties;

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
	private	static Properties commandLineProperties = new Properties();

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
	
	public static void loadCustomCommandLineArgs(String[] args) {
		Arrays.stream(args).forEach(arg -> {
			arg = arg.startsWith("-") ? arg.substring(1) : arg;
			String[] argValue = arg.split("=");
			String key = argValue[0];
			String value = argValue[1];
			Log.base.debug("Loaded CommandLineArg - key : {} & value : {}", key, value);
			commandLineProperties.put(key, value);
		});
	}
	
	public static String getCLAProperty(String key) {
		return commandLineProperties.getProperty(key);
	}

}
