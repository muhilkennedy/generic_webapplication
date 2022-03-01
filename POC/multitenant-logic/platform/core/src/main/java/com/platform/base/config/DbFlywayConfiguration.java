package com.platform.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class DbFlywayConfiguration {

    private final DataSourceProperties dataSourceProperties;
    
//    @Resource(name = "dsa")
//    List<DataSource> dsall = new ArrayList<DataSource>();
//    
//    @Resource(name = "ds")
//    DataSource d;

    public DbFlywayConfiguration(DataSourceProperties dataSourceProperties) {
    	System.out.print("DataSourceConfiguration constructor");
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource customDataSource = new TenantRoutingDataSource();
//        customDataSource.setDefaultTargetDataSource(customDataSource);
        customDataSource.setTargetDataSources(dataSourceProperties.getDatasource());
        return customDataSource;
    }

    @PostConstruct
    public void migrate() {
        dataSourceProperties
                .getDatasource()
                .values()
                .stream()
                .map(dataSource -> (DataSource) dataSource)
                .forEach(this::migrate);
        
    }

    private void migrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        System.out.println("-----Started Flyway migration-----");
        flyway.migrate();
        System.out.println("-----Completed Flyway migration-----");
    }
}
