package com.platform.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.platform.base.entity.City;

public interface CityRepository extends JpaRepository<City,Long> {

//    Optional<City> findById(Long id);

    City findByName(String name);
//
    void deleteByName(String name);
    
    String findTenantByIDQuery = "select realm from City realm";
	
	@Query(findTenantByIDQuery)
	List<City> findTenantByNameQuery();
}