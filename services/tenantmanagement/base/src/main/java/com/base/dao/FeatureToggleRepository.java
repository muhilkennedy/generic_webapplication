package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base.entity.FeatureToggle;

/**
 * @author Muhil Kennedy
 *
 */
@Repository
public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, String>{
	
}
