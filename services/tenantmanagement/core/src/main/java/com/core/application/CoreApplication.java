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

@SpringBootApplication
@ComponentScan(basePackages = { "com.i18n", "com.core", "com.base", "com.tenant", "com.user" })
@EnableConfigurationProperties(DatabaseFieldProperties.class)
@ConfigurationPropertiesScan(basePackages = { "com.i18n", "com.core", "com.base", "com.tenant", "com.user" })
@EntityScan(basePackages = { "com.i18n", "com.core", "com.base", "com.tenant", "com.user" })
@EnableJpaRepositories(basePackages = { "com.i18n", "com.core", "com.base", "com.tenant", "com.user" })
@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy // (exposeProxy=true)
@EnableDiscoveryClient
public class CoreApplication {

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting Core Application >>>>>>>>>>>>>");
		SpringApplication.run(CoreApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}

}
