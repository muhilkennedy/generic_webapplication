package com.platform.tenant.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 1L;

	//read this from prop file
	String DEFAULT_TENANT_ID = "default";
	
	@Autowired
	@Qualifier("ds")
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    private Map<String, DataSource> map = new HashMap<>();

    public static boolean init = true;

    @PostConstruct
    public void load() {
        map.put("default", defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get("default");
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (init) {
        	map.clear();
            init = false;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getAll());
        }
        //TODO: throw invalid tenant if possible
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }
}
