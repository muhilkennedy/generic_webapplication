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

	public PlatformConfiguration(Properties frontDoorProperties, Properties appProperties) {
		PlatformConfiguration.frontDoorProperties = frontDoorProperties;
		PlatformConfiguration.appProperties = appProperties;
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

	public static class Builder {
		private Properties frontDoorProperties;
		private Properties appProperties;

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

		public PlatformConfiguration build() {
			Objects.requireNonNull(this.frontDoorProperties, "Front door url properties cannot be null");
			// validate frontdoor urls
			PropertiesUtil.getMandatoryProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(property, String.format("Property %s is missing", property));
			});
			return new PlatformConfiguration(frontDoorProperties, appProperties);
		}

	}

}
