package com.platform.core.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@SpringBootApplication
@ComponentScan(basePackages = { "com.platform.base", "com.platform.core" }) //, "com.platform.tenant", "com.platform.user", "com.platform.platformcore" })
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = { "com.platform.base", "com.platform.core" })
@EntityScan(basePackages = { "com.platform.base", "com.platform.core" })
@EnableJpaRepositories(basePackages = { "com.platform.base", "com.platform.core" })
@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy//(exposeProxy=true)
public class PlatformApplication {

	private static Logger logger = LoggerFactory.getLogger(PlatformApplication.class);
	
	@Autowired
	private BaseService baseService;
	
	public static void main(String[] args) {
		logger.info("<<<<<<<<<<<<< Starting PlatformApplication >>>>>>>>>>>>>");
		SpringApplication.run(PlatformApplication.class, args);
		logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
//		logger.info("<<<<<<<<<<<<< " + ConfigurationUtil.getAppName() + " Started in : " + ConfigurationUtil.getDeploymentMode() + " >>>>>>>>>>>>>");
//		logger.info("<<<<<<<<<<<<< App Running in port : " + ConfigurationUtil.getServerPort() + " >>>>>>>>>>>>>");
//		logger.info("<<<<<<<<<<<<< AOP Logging Enabled : " + ConfigurationUtil.isAopLogging() + " >>>>>>>>>>>>>");
//		logger.info("<<<<<<<<<<<<< DBMigration Enabled : " + ConfigurationUtil.isFlywayEnabled() + " >>>>>>>>>>>>>");
	}
	
}
