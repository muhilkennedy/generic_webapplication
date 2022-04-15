package com.platform.base.flyway.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.platform.base.util.Log;

@Configuration
@Component
public class DbFlywayConfiguration {

	private final FlywayDataSourceProperties dataSourceProperties;

	public DbFlywayConfiguration(FlywayDataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	@PostConstruct
	public void migrate() {
		dataSourceProperties.getDatasource().values().stream().map(dataSource -> (DataSource) dataSource)
				.forEach(this::migrate);
	}

	private void migrate(DataSource dataSource) {
		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		Log.logger.warn("----- Started Flyway migration " + dataSource.toString() + " -----");
		flyway.migrate();
		Log.logger.warn("----- Completed Flyway migration for " + dataSource.toString() + " -----");
	}
}
