package com.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.api.Product;
import com.core.jpa.ProdRepo;
import com.core.repo.ProductRepo;

import reactor.core.publisher.Mono;

@Service
public class AbstractService {
	
	@Autowired
	protected ProductRepo repo;
	
	@Autowired
	protected ProdRepo jpaRepo;
	
	@Cacheable(value="product")
	public Product getProduct(int id) {
		System.out.println("fetching from db - " + id);
		Product prod = repo.findById(id).block();
		return prod;
	}
	
	@CachePut(value="product", key="#product.id")
	public Product save(Product product) {
		product = repo.save(product).block();
		return product;
	}
	
	@Cacheable(value="product")
	public Product getProductJpa(int id) {
		return jpaRepo.findById(id).get();
	}
	
	@CachePut(value="product", key="#prod.id")
	//@CacheEvict(value="product", key="#prod.id")
	public Product saveJPA(Product prod) {
		return jpaRepo.saveAndFlush(prod);
	}


}
