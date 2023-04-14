package com.mken.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mken.base.util.Log;

@SpringBootApplication
@ComponentScan(basePackages = { "com.mken.base.*", "com.mken.user.*" })
@ConfigurationPropertiesScan(basePackages = { "com.mken.base.*", "com.mken.user.*" })
@EntityScan(basePackages = { "com.mken.base.*","com.mken.user.*" })
@EnableR2dbcRepositories(basePackages = { "com.mken.user.r2db.repo" })
@EnableJpaRepositories(basePackages = { "com.mken.user.jpa.repo" })
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class TravelApplication {

	public static void main(String[] args) {
		Log.logger.info("<<<<<<<<<<<<< Starting Travel Application >>>>>>>>>>>>>");
		SpringApplication.run(TravelApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
	}

}
