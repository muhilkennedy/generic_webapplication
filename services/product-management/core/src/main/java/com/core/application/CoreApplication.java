package com.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.core" })
@ConfigurationPropertiesScan(basePackages = { "com.core" })
@EntityScan(basePackages = { "com.core" })
@EnableAutoConfiguration
@EnableR2dbcRepositories(basePackages = { "com.core.repo" })
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
	
}
