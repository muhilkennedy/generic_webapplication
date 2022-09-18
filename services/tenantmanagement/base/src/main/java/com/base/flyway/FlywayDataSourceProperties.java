package com.base.flyway;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

//To fetch multiple datasources
//@Configuration
//@PropertySource({ "classpath:application-tenant.properties" })
//@ConfigurationProperties(prefix = "tenants")
//@EnableTransactionManagement
//public class FlywayDataSourceProperties {
//
//	private Map<Object, Object> datasource = new LinkedHashMap<Object, Object>();
//
//	public Map<Object, Object> getDatasource() {
//		return datasource;
//	}
//
//	public void setDatasource(Map<String, Map<String, String>> datasources) {
//		int i = 1;
//		for (Entry<String, Map<String, String>> dataSource1 : datasources.entrySet()) {
//			this.datasource.put(dataSource1.getKey(), convert(dataSource1.getValue(), i));
//			i++;
//		}
//
//	}
//
//	public void addNewDs(String key, Map<String, String> values) {
//		this.datasource.put(key, convert(values, this.datasource.size() + 1));
//	}
//
//	public DataSource convert(Map<String, String> source, int count) {
//    	/*DataSource dt = DataSourceBuilder.create()
//                .url(source.get("jdbcUrl"))
//                .driverClassName(source.get("driverClassName"))
//                .username(source.get("username"))
//                .password(source.get("password"))
//                .build();*/
//		HikariDataSource hik = new HikariDataSource();
//		setUp(hik, source, count);
//		return hik;
//	}
//
//	private void setUp(HikariDataSource dataSrc, Map<String, String> source, int count) {
//		dataSrc.setPoolName("hikariDataSource" + count);
//		dataSrc.setMaximumPoolSize(20);
//		dataSrc.setLeakDetectionThreshold(300000);
//		dataSrc.setMaxLifetime(0);
//		dataSrc.setConnectionTimeout(3000);
//		StringBuilder str = new StringBuilder();
//		str.append(source.get("jdbcUrl"));
//		dataSrc.setJdbcUrl(str.toString());
//		dataSrc.setUsername(source.get("username"));
//		dataSrc.setPassword(source.get("password"));
//		dataSrc.setDriverClassName(source.get("driverClassName"));
//
//		Properties aditionalPros = new Properties();
//		aditionalPros.put("serverName", "jdbc:mysql://localhost:3306");
//		aditionalPros.put("schema", source.get("schema"));
//		aditionalPros.put("portNumber", 3306);
//
//		dataSrc.setDataSourceProperties(aditionalPros);
//	}
//
//	@Bean(name = { "ds" })
//	@Primary
//	public DataSource dataSourceSystem() {
//		return (DataSource) this.datasource.get("default");
//	}
//
//	@Bean(name = { "dsa" })
//	public List<DataSource> dataSourceSystem1() {
//		List<DataSource> ds = new ArrayList<DataSource>();
//		this.datasource.entrySet().forEach(entry -> {
//			ds.add((DataSource) entry.getValue());
//		});
//		return ds;
//
//	}
//
//}