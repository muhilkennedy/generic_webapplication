package com.core.application;

import org.flywaydb.database.mysql.MySQLConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.mysql.cj.jdbc.MysqlDataSourceFactory;

import io.r2dbc.spi.ConnectionFactory;

//@Configuration
//@EnableR2dbcRepositories
//class SQLConfiguration extends AbstractR2dbcConfiguration {
//
//	@Override
//	public ConnectionFactory connectionFactory() {
//		// TODO Auto-generated method stub
//		return new MYSQ;
//	}
//    
//
//}