package com.platform.util;

import java.util.Arrays;
import java.util.List;

import com.platform.configuration.PlatformConfiguration;

/**
 * @author Muhil
 *
 */
public class PlatformPropertiesUtil {
	
	public static final String KEY_TENANT_FRONTDOOR = "service.tm.frontdoor";
	public static final String KEY_USER_FRONTDOOR = "service.user.frontdoor";
	
	public static final String KEY_MAIL_HOST = "mail.smtp.host";
	public static final String KEY_MAIL_PORT = "mail.smtp.port";
	public static final String KEY_MAIL_AUTH = "mail.smtp.auth";
	public static final String KEY_MAIL_SOCKET_CLASS = "mail.smtp.socketFactoryClass";
	public static final String KEY_DEFAULT_EMAIL = "app.admin.email";
	public static final String KEY_DEFAULT_PASSWORD = "app.admin.email.password";
	
	public static final String KEY_DB_SECRET = "encryption.db.secret";
	public static final String KEY_FILE_SECRET = "encryption.file.secret";
	public static final String KEY_JWT_SECRET = "encryption.jwt.secret";
	
	public static List<String> getMandatoryFrontdoorProperties() {
		return Arrays.asList(KEY_TENANT_FRONTDOOR, KEY_USER_FRONTDOOR);
	}

	public static List<String> getMandatoryEmailProperties() {
		return Arrays.asList(KEY_DEFAULT_EMAIL, KEY_DEFAULT_PASSWORD,
				KEY_MAIL_AUTH, KEY_MAIL_HOST, KEY_MAIL_PORT);
	}
	
	public static List<String> getMandatorySecretsProperties() {
		return Arrays.asList(KEY_DB_SECRET, KEY_FILE_SECRET, KEY_JWT_SECRET);
	}

	public static String getTMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(KEY_TENANT_FRONTDOOR);
	}

	public static String getUMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(KEY_USER_FRONTDOOR);
	}
	
	public static String getDefaultEmail() {
		return PlatformConfiguration.getEmailProperties().getProperty(KEY_DEFAULT_EMAIL);
	}
	
	public static String getDefaultEmailPassword() {
		return PlatformConfiguration.getEmailProperties().getProperty(KEY_DEFAULT_PASSWORD);
	}
	
	public static String getDbSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_DB_SECRET);
	}

	public static String getFileSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_FILE_SECRET);
	}

	public static String getJwtSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_JWT_SECRET);
	}

}
