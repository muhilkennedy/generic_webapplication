package com.platform.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.base.entity.DataSourceConfig;

public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long> {
    DataSourceConfig findByTenantId(String tenantId);
}
