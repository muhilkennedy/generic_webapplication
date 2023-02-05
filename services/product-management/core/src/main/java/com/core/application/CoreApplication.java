package com.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.base.util.Log;

@SpringBootApplication
@ComponentScan(basePackages = { "com.base", "com.i18n", "com.service", "com.core" })
@ConfigurationPropertiesScan(basePackages = { "com.base", "com.i18n", "com.service", "com.core" })
@EntityScan(basePackages = { "com.base", "com.i18n", "com.service", "com.core" })
@EnableR2dbcRepositories(basePackages = { "com.base", "com.i18n", "com.service", "com.core" })
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
public class CoreApplication {

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting Core Application >>>>>>>>>>>>>");
		SpringApplication.run(CoreApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}
	
}
