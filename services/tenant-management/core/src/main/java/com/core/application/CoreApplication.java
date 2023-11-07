package com.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.base.security.DatabaseFieldProperties;
import com.base.util.Log;
import com.base.util.PropertiesUtil;

/**
 * @author Muhil Kennedy
 * @implNote: make sure to add entity scan packages in base module hibernate config.
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.i18n", "com.base", "com.tenant", "com.user", "com.core" })
@EnableConfigurationProperties(DatabaseFieldProperties.class)
@ConfigurationPropertiesScan(basePackages = { "com.i18n", "com.base", "com.tenant", "com.user", "com.core" })
@EntityScan(basePackages = { "com.i18n", "com.base", "com.tenant", "com.user", "com.core" })
@EnableJpaRepositories(basePackages = { "com.i18n", "com.base", "com.tenant", "com.user", "com.core" })
@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy//(exposeProxy=true)
@EnableDiscoveryClient
public class CoreApplication {

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting Core Application >>>>>>>>>>>>>");
		PropertiesUtil.loadCustomCommandLineArgs(args);
		SpringApplication.run(CoreApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}

}
