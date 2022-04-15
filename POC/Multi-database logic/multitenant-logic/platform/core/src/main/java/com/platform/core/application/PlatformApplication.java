package com.platform.core.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.platform.base.service.BaseService;
import com.platform.base.util.Log;

@SpringBootApplication
@ComponentScan(basePackages = { "com.platform.base", "com.platform.core", "com.platform.tenant" })
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = { "com.platform.base", "com.platform.core", "com.platform.tenant" })
@EntityScan(basePackages = { "com.platform.base", "com.platform.core", "com.platform.tenant" })
@EnableJpaRepositories(basePackages = { "com.platform.base", "com.platform.core", "com.platform.tenant" })
@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy // (exposeProxy=true)
public class PlatformApplication {

	@Autowired
	private BaseService baseService;

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting PlatformApplication >>>>>>>>>>>>>");
		SpringApplication.run(PlatformApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}

}
