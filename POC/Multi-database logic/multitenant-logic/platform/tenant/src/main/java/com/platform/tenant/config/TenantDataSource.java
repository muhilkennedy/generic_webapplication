package com.platform.tenant.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.platform.base.entity.DataSourceConfig;
import com.platform.base.flyway.config.DbFlywayConfiguration;
import com.platform.base.flyway.config.FlywayDataSourceProperties;
import com.platform.tenant.repository.DataSourceConfigRepository;

@Configuration
@Component
public class TenantDataSource {

    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private DataSourceConfigRepository configRepo;

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = buildDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }
    
    public void createAndAddNewDataSourceInRunTime(String tenantId, Map<String, String> databaseInfo) {
    	createDataSource(tenantId, databaseInfo);
    	DataSource dataSource = buildDataSource(tenantId);
    	dataSources.put(tenantId, dataSource);
    	DataSourceBasedMultiTenantConnectionProviderImpl.init = true;
    }
 
    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = configRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceConfig config : configList) {
            DataSource dataSource = getDataSource(config.getTenantId());
            result.put(config.getTenantId(), dataSource);
        }
        return result;
    }

    private DataSource buildDataSource(String name) {
        DataSourceConfig config = configRepo.findByTenantId(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());
            DataSource ds = factory.build();     
            return ds;
        }
        return null;
    }
    
    private DataSourceConfig createDataSource(String tenantId, Map<String,String> dbInfo) {
    	DataSourceConfig config = new DataSourceConfig();
    	config.setDriverClassName(dbInfo.get("driverClassName"));
    	config.setInitialize(true);
    	config.setUrl(dbInfo.get("jdbcUrl"));
    	config.setUsername(dbInfo.get("username"));
    	config.setPassword(dbInfo.get("password"));
    	config.setTenantId(tenantId);
    	config.setId((long) (this.dataSources.size()+1));
    	configRepo.save(config);
    	return config;
    }
    
    /**
     * Config bean
     * */
	private final FlywayDataSourceProperties dataSourceProperties;
	
	public TenantDataSource(FlywayDataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	@Bean
	public DataSource dataSource() {
		TenantRoutingDataSource customDataSource = new TenantRoutingDataSource();
		customDataSource.setTargetDataSources(dataSourceProperties.getDatasource());
		return customDataSource;
	}
}