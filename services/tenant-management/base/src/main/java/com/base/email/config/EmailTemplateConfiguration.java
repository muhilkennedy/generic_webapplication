package com.base.email.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * @author Muhil
 * Free marker templates used for email body generation.
 */
@Configuration
@PropertySource("classpath:application-base.properties")
public class EmailTemplateConfiguration {

	@Value("${app.admin.email}")
	private String adminEmailId;

	@Value("${app.admin.email.password}")
	private String adminEmailPassword;
	
	@Value("${app.email.templates.path}")
	private String[] templatePath;

	@Autowired
	private EmailHostConfig hostConfig;

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean emailTemplateBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPaths(templatePath);
		return bean;
	}

	@PostConstruct
	private void intializeGmailConnection() {
		Properties gmailProps = new Properties();
		gmailProps.put("mail.smtp.host", hostConfig.getHost());
		//gmailProps.put("mail.smtp.socketFactory.port", hostConfig.getSocketFactoryPort());
		gmailProps.put("mail.smtp.socketFactory.class", hostConfig.getSocketFactoryClass());
		gmailProps.put("mail.smtp.auth", hostConfig.getAuth());
		gmailProps.put("mail.smtp.port", hostConfig.getPort());
		Properties authProps = new Properties();
		authProps.put("app.admin.email", adminEmailId);
		authProps.put("app.admin.email.password", adminEmailPassword);
		EmailConnectionProperties emailConnectionProperties = new EmailConnectionProperties(gmailProps, authProps);
		EmailSystem.Builder emailSystemBuilder = new EmailSystem.Builder();
		emailSystemBuilder.withEmailProperties(emailConnectionProperties).build();
	}

}
