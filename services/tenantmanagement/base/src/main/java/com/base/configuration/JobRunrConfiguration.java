package com.base.configuration;

import javax.sql.DataSource;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration.JobRunrConfigurationResult;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Muhil
 * Background job runr config. Default data source used for job tracking.
 */
@Configuration
public class JobRunrConfiguration {
	
	@Bean
    public JobRunrConfigurationResult initJobRunr(DataSource dataSource, JobActivator jobActivator) {
        return JobRunr.configure()
                .useJobActivator(jobActivator)
                .useStorageProvider(SqlStorageProviderFactory
                          .using(dataSource))
                .useBackgroundJobServer()
                .useDashboard()
                .initialize();
    }

}
