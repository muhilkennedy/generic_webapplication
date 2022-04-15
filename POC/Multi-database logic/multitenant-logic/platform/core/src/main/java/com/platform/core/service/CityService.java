package com.platform.core.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.base.entity.City;
import com.platform.base.repository.CityRepository;
import com.platform.base.service.BaseService;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    BaseService baseService;
    
    public EntityManager entityManager() {
    	return this.entityManager;
    }

    public void save(City city){
        cityRepository.save(city);
    }

    public List<City> getAll() throws SQLException {
        return cityRepository.findAll();

    }

    public Optional<City> get(Long id){
        return cityRepository.findById(id);
    }

    public City getByName(String name){
        return cityRepository.findByName(name);
    }

    public void delete(String name){
        cityRepository.deleteByName(name);
    }
    
    public List<City> getAllRealmdata(){
    	return cityRepository.findTenantByNameQuery();
    }
}