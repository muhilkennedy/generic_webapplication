package com.tenant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenant.entity.TenantOrigin;

public interface TenantOriginRepository extends JpaRepository<TenantOrigin, String> {

}
