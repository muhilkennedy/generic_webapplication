package com.platform.base.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix = "tenants")
@EnableTransactionManagement
public class DataSourceProperties {

    private Map<Object, Object> datasource = new LinkedHashMap();

    public Map<Object, Object> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Map<String, String>> datasources) {
//    	this.datasource.put("asc", dataSourceSystem());
    	int i = 1;
    	for(Entry<String, Map<String, String>> dataSource1 : datasources.entrySet()) {
        	this.datasource.put(dataSource1.getKey(), convert(dataSource1.getValue(), i));
        	i++;
        }
        
    }

    public DataSource convert(Map<String, String> source, int count) {
    	DataSource dt = DataSourceBuilder.create()
                .url(source.get("jdbcUrl"))
                .driverClassName(source.get("driverClassName"))
                .username(source.get("username"))
                .password(source.get("password"))
                .build();
    	HikariDataSource hik = new HikariDataSource();
    	setUp(hik, source, count);
        return hik;
    }
    
//    public void setDatasource(Map<Object, Object> datasource) {
//		this.datasource = datasource;
//	}

	private void setUp(HikariDataSource dataSrc, 
    		Map<String, String> source, int count) {
    	dataSrc.setPoolName("ascHikariDataSource"+count);
    	dataSrc.setMaximumPoolSize(20);
    	dataSrc.setLeakDetectionThreshold(300000);
    	dataSrc.setMaxLifetime(0);
    	dataSrc.setConnectionTimeout(3000);
    	StringBuilder str = new StringBuilder();
    	str.append(source.get("jdbcUrl"));
//    	str.append("&user=").append(source.get("username"));
//    	str.append("password=").append(source.get("password"));
    	dataSrc.setJdbcUrl(str.toString());
    	dataSrc.setUsername(source.get("username"));
    	dataSrc.setPassword(source.get("password"));
    	dataSrc.setDriverClassName(source.get("driverClassName"));
    	
    	
    	Properties aditionalPros = new Properties();
    	aditionalPros.put("serverName", "jdbc:mysql://localhost:3306");
    	aditionalPros.put("schema", source.get("schema"));
    	aditionalPros.put("portNumber", 3306);
    	
    	
    	dataSrc.setDataSourceProperties(aditionalPros);
    }
    
    @Bean(name = {"ds"})
    @Primary
    public DataSource dataSourceSystem() {
    	
    	HikariDataSource hik = new HikariDataSource();
    	Map<String, String> source = new HashedMap<String, String>();
    	source.put("jdbcUrl", "jdbc:mysql://localhost:3306/platformdb?useSSL=false&allowPublicKeyRetrieval=true");
    	source.put("driverClassName", "com.mysql.jdbc.Driver");
    	source.put("username", "root");
    	source.put("password", "root@123");
    	source.put("schema", "platformdb");
    	setUp(hik, source, 0);
    	
		return hik;
    		
    }
    
    @Bean(name = {"dsa"})
    public List<DataSource> dataSourceSystem1() {
    	List<DataSource> ds = new ArrayList<DataSource>();
    	
    	HikariDataSource hik = new HikariDataSource();
    	Map<String, String> source = new HashedMap<String, String>();
    	source.put("jdbcUrl", "jdbc:mysql://localhost:3306/platformdb1?useSSL=false&allowPublicKeyRetrieval=true");
    	source.put("driverClassName", "com.mysql.jdbc.Driver");
    	source.put("username", "root");
    	source.put("password", "root@123");
    	source.put("schema", "platformdb1");
    	setUp(hik, source, 1);
    	
    	ds.add(hik);
    	
    	HikariDataSource hik1 = new HikariDataSource();
    	Map<String, String> source1 = new HashedMap<String, String>();
    	source1.put("jdbcUrl", "jdbc:mysql://localhost:3306/platformdb2?useSSL=false&allowPublicKeyRetrieval=true");
    	source1.put("driverClassName", "com.mysql.jdbc.Driver");
    	source1.put("username", "root");
    	source1.put("password", "root@123");
    	source1.put("schema", "platformdb2");
    	setUp(hik1, source1, 2);
    	
    	ds.add(hik1);
    	
    	
		return ds;
    		
    }
    
}