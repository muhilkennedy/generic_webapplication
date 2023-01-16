package com.platform.configuration;

import java.util.Objects;
import java.util.Properties;

import org.apache.http.util.Asserts;

import com.platform.util.PropertiesUtil;

/**
 * @author Muhil
 * Builder class to initialize default properties.
 *
 */
public class PlatformConfiguration {

	private static Properties frontDoorProperties;
	private static Properties appProperties;
	private static Properties emailProperties;

	public PlatformConfiguration(Properties frontDoorProperties, Properties appProperties, Properties emailProperties) {
		PlatformConfiguration.frontDoorProperties = frontDoorProperties;
		PlatformConfiguration.appProperties = appProperties;
		PlatformConfiguration.emailProperties = emailProperties;
	}

	public static Properties getFrontDoorProperties() {
		return frontDoorProperties;
	}

	public static void setFrontDoorProperties(Properties frontDoorProperties) {
		PlatformConfiguration.frontDoorProperties = frontDoorProperties;
	}

	public static Properties getAppProperties() {
		return appProperties;
	}

	public static void setAppProperties(Properties appProperties) {
		PlatformConfiguration.appProperties = appProperties;
	}

	public static Properties getEmailProperties() {
		return emailProperties;
	}

	public static void setEmailProperties(Properties emailProperties) {
		PlatformConfiguration.emailProperties = emailProperties;
	}

	public static class Builder {
		private Properties frontDoorProperties;
		private Properties appProperties;
		private Properties emailProperties;

		public Builder() {
		}

		public PlatformConfiguration.Builder withFrontDoorProperties(Properties frontDoorProperties) {
			this.frontDoorProperties = frontDoorProperties;
			return this;
		}

		public PlatformConfiguration.Builder withAppProperties(Properties appProperties) {
			this.appProperties = appProperties;
			return this;
		}
		
		public PlatformConfiguration.Builder withEmailProperties(Properties emailProperties) {
			this.emailProperties = emailProperties;
			return this;
		}

		public PlatformConfiguration build() {
			Objects.requireNonNull(this.frontDoorProperties, "Front door url properties cannot be null");
			Objects.requireNonNull(this.emailProperties, "Email configuration properties cannot be null");
			// validate frontdoor urls
			PropertiesUtil.getMandatoryFrontdoorProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(this.frontDoorProperties.getProperty(property),
						String.format("Property %s is missing", property));
			});
			PropertiesUtil.getMandatoryEmailProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(this.frontDoorProperties.getProperty(property),
						String.format("Property %s is missing", property));
			});
			return new PlatformConfiguration(frontDoorProperties, appProperties, emailProperties);
		}

	}

}
