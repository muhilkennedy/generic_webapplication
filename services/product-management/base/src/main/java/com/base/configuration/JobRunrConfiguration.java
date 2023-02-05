package com.base.configuration;

import javax.sql.DataSource;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration.JobRunrConfigurationResult;
import org.jobrunr.dashboard.JobRunrDashboardWebServerConfiguration;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Muhil
 * Background job runr config. Default data source used for job tracking.
 */
@Configuration
public class JobRunrConfiguration {
	
	@Value("${org.jobrunr.dashboard.port}")
	private int jobRunrPort;
	
	 @Bean
	    public DataSource getDataSource() {
	        return DataSourceBuilder.create()
	          .driverClassName("com.mysql.jdbc.Driver")
	          .url("jdbc:mysql://localhost:3306/productmanagement?useSSL=false&allowPublicKeyRetrieval=true")
	          .username("root")
	          .password("root@123")
	          .build();	
	    }
	
	@Bean
    public JobRunrConfigurationResult initJobRunr(DataSource dataSource, JobActivator jobActivator) {
		JobRunrDashboardWebServerConfiguration dashBoardConfig = JobRunrDashboardWebServerConfiguration.usingStandardDashboardConfiguration().andPort(jobRunrPort);
        return JobRunr.configure()
                .useJobActivator(jobActivator)
                .useStorageProvider(SqlStorageProviderFactory
                          .using(dataSource))
                .useBackgroundJobServer()
                .useDashboard(jobRunrPort+1)//runs in two diff ports to avoid default address 8000 conflict during initialization.
                .initialize();
    }

}
