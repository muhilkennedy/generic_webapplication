package com.core.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.core.api.Product;

@Repository
public interface ProductRepo extends R2dbcRepository<Product, Integer> {
	
	

}
