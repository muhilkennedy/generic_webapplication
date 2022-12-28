package com.base.hiberbate.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.base.util.Constants;

@Configuration
public class HibernateConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private HibernateInterceptor hibernateInterceptor;

	@Bean
	JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	// use incase of separate schema identification needed
	/*@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
		Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
		jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR);
		jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
		jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
		jpaPropertiesMap.put(Environment.FORMAT_SQL, true);
		jpaPropertiesMap.put(Environment.SHOW_SQL, true);

		jpaPropertiesMap.put(Constants.Key_Hibernate_Interceptor, hibernateInterceptor);

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(Constants.BASE_PACKAGE, Constants.CORE_PACKAGE, Constants.TENANT_PACKAGE);
		em.setJpaVendorAdapter(this.jpaVendorAdapter());
		em.setJpaPropertyMap(jpaPropertiesMap);
		return em;
	}*/
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factory,
			DataSource dataSource, JpaProperties properties) {
		Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
		jpaPropertiesMap.put(Constants.Key_Hibernate_Interceptor, hibernateInterceptor);
		//jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(Constants.BASE_PACKAGE, Constants.CORE_PACKAGE, Constants.TENANT_PACKAGE, Constants.USER_PACKAGE);
		em.setJpaVendorAdapter(this.jpaVendorAdapter());
		em.setJpaPropertyMap(jpaPropertiesMap);
		return em;
	}
	
//	@Bean
//	public EntityManagerFactory customEntityManagerFactory(DataSource dataSource) {
//	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//	    vendorAdapter.setGenerateDdl(false); // turn off with Discriminator strategy so far!
//	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//	    factory.setJpaVendorAdapter(vendorAdapter);
//	    factory.setPackagesToScan(Constants.BASE_PACKAGE, Constants.CORE_PACKAGE, Constants.TENANT_PACKAGE);
//	    factory.setDataSource(dataSource);//from w w w  .  ja  v  a2 s.  c  o m
//	    factory.getJpaPropertyMap().put(Environment.DIALECT, MySQL5InnoDBDialect.class.getName());
//	    factory.getJpaPropertyMap().put(Environment.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR);
//	    factory.getJpaPropertyMap().put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, new TenantHolder());
//	    factory.afterPropertiesSet();
//	    return factory.getObject();
//	}

}