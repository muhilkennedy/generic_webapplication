package com.base.configuration;

import javax.sql.DataSource;

//import org.jobrunr.configuration.JobRunr;
//import org.jobrunr.configuration.JobRunrConfiguration.JobRunrConfigurationResult;
//import org.jobrunr.dashboard.JobRunrDashboardWebServerConfiguration;
//import org.jobrunr.server.JobActivator;
//import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import io.r2dbc.spi.ConnectionFactory;

/**
 * @author Muhil
 * Background job runr config. Default data source used for job tracking.
 */
@Configuration
@EnableTransactionManagement
public class JobRunrConfiguration {
	
	@Value("${org.jobrunr.dashboard.port}")
	private int jobRunrPort;
	
//	@Bean("chainedTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("tm1") final PlatformTransactionManager db1PlatformTransactionManager) {
//
//        return new ChainedTransactionManager(db1PlatformTransactionManager);
//    }
	
	@Bean
	//@Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
          .driverClassName("com.mysql.jdbc.Driver")
          .url("jdbc:mysql://localhost:3306/productmanagement?useSSL=false&allowPublicKeyRetrieval=true")
          .username("root")
          .password("root@123")
          .build();	
    }
	
//	@Bean
//	@Qualifier("tm")
//	TransactionManagementConfigurer transactionManagementConfigurer(ReactiveTransactionManager reactiveTransactionManager) {
//	    return new TransactionManagementConfigurer() {
//	        @Override
//	        public TransactionManager annotationDrivenTransactionManager() {
//	            return reactiveTransactionManager;
//	        }
//	    };
//	}
	
//	@Bean
//	@Primary
//	@Qualifier("jpa")
//	public TransactionManager transactionManager() {
//	    return new JpaTransactionManager();
//	}
//	
//	@Bean
//	@Qualifier("rdc")
//	@Primary
//	public TransactionManager transactionManager1(ConnectionFactory connection) {
//		R2dbcTransactionManager mgm = new R2dbcTransactionManager();
//	    mgm.setConnectionFactory(connection);
//	    return mgm;
//	}
	

	
//	
//	@Primary
//	@Bean(name="tm1") 
//	@Autowired
//	DataSourceTransactionManager tm1(@Qualifier ("datasource1") DataSource datasource) {
//	    DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
//	    return txm;
//	}
//	

//	@Bean(name="tm2")
//	@Autowired
//	DataSourceTransactionManager tm2(DataSource datasource) {
//	    DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
//	    return txm;
//	}
//	
	//@Primary
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource appDataSource() {
//	    return DataSourceBuilder.create().build();
//	}

//	@Bean
//	@ConfigurationProperties(prefix = "datasource.batch")
//	public DataSource batchDataSource() {
//	    return DataSourceBuilder.create().build();
//	}
	
	
//	@Bean
//    public JobRunrConfigurationResult initJobRunr(DataSource dataSource, JobActivator jobActivator) {
//		JobRunrDashboardWebServerConfiguration dashBoardConfig = JobRunrDashboardWebServerConfiguration.usingStandardDashboardConfiguration().andPort(jobRunrPort);
//        return JobRunr.configure()
//                .useJobActivator(jobActivator)
//                .useStorageProvider(SqlStorageProviderFactory
//                          .using(dataSource))
//                .useBackgroundJobServer()
//                .useDashboard(jobRunrPort+1)//runs in two diff ports to avoid default address 8000 conflict during initialization.
//                .initialize();
//    }

}
