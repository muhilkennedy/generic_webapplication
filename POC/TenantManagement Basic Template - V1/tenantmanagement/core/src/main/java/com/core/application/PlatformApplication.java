package com.core.application;

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

import com.base.util.Log;

@SpringBootApplication
@ComponentScan(basePackages = { "com.core", "com.base", "com.tenant" })
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = { "com.core", "com.base", "com.tenant" })
@EntityScan(basePackages = { "com.core", "com.base", "com.tenant" })
@EnableJpaRepositories(basePackages = { "com.core", "com.base", "com.tenant" })
@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy // (exposeProxy=true)
public class PlatformApplication {

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting PlatformApplication >>>>>>>>>>>>>");
		SpringApplication.run(PlatformApplication.class, args);
		System.out.println("app started with changes");
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}

}
