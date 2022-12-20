package com.base.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.configuration.DataSourceProperties;

@Component
public class DatabaseUtil {
	
	private static DataSourceProperties dbProperties;

	@Autowired
	public void setDbProperties(DataSourceProperties props) {
		DatabaseUtil.dbProperties = props;
	}

	public static Connection getConnectionInstance() throws Exception {
		return DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(),
				dbProperties.getPassword());
	}
}
