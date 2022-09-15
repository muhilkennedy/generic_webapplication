package com.tenant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenant.entity.TenantDetails;

/**
 * @author Muhil
 *
 */
@Repository
public interface TenantDetailsRepository extends JpaRepository<TenantDetails, String> {

}
