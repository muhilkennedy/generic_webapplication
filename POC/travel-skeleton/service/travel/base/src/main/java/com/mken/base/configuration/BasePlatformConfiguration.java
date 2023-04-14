package com.mken.base.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mken.base.util.PropertiesUtil;
import com.platform.configuration.PlatformConfiguration;
import com.platform.util.PlatformPropertiesUtil;

@Configuration
public class BasePlatformConfiguration {

	private PlatformConfiguration platformConfig;

	@Autowired
	private Environment env;

	@PostConstruct
	private void initialize() {
		PropertiesUtil.setEnvironment(env);
		Properties frontDoorProperties = new Properties();
		PlatformPropertiesUtil.getMandatoryFrontdoorProperties().stream().forEach(property -> {
			frontDoorProperties.put(property, PropertiesUtil.getProperty(property));
		});
		Properties emailProperties = new Properties();
		PlatformPropertiesUtil.getMandatoryEmailProperties().stream().forEach(property -> {
			emailProperties.put(property, PropertiesUtil.getProperty(property));
		});
		Properties encryptionProperties = new Properties();
		PlatformPropertiesUtil.getMandatorySecretsProperties().stream().forEach(property -> {
			encryptionProperties.put(property, PropertiesUtil.getProperty(property));
		});
		platformConfig = new PlatformConfiguration.Builder().withFrontDoorProperties(frontDoorProperties)
				.withEmailProperties(emailProperties).withEncryptionProperties(encryptionProperties)
				.withAppName(PropertiesUtil.getProperty("spring.application.name")).isMultitenantApp(false).build();
	}

}
