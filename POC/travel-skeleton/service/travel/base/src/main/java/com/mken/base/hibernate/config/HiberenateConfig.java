package com.mken.base.hibernate.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HiberenateConfig {
	
	@Value("${spring.datasource.url}")
	private String dsUrl;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Bean
	//@Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
          .driverClassName(driver)
          .url(dsUrl)
          .username(userName)
          .password(password)
          .build();	
    }

}
