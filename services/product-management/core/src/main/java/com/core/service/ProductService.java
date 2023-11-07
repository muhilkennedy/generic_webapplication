package com.core.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.api.Product;
import com.core.jpa.ProdRepo;
//import com.core.jpa.ProdRepo;
import com.core.repo.ProductRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	protected ProductRepo repo;
	
	@Autowired
	protected ProdRepo jpaRepo;
	
	@Autowired
	private AbstractService service;
	
	public Flux<Product> getProducts() {
		List<Product> prods = new ArrayList<>();
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1)).doOnNext(i -> System.out.println(i))
				.map(i -> new Product("item" + i, i));
	}

	public Mono<Product> update(int id, String name) {
		Product prod = service.getProduct(id);
		prod.setName(name);
		service.save(prod);
		return Mono.just(prod);
	}
	
	public Mono<Product> save() {
		Product prod = new Product("name", new SecureRandom().nextInt());
		return repo.save(prod);
	}
	
	public Flux<Product> saveALL() {
		List<Product> prods = new ArrayList<>();
		for(int i=0; i<10000; i++) {
			Product prod = new Product("name", new SecureRandom().nextInt());
			prods.add(prod);
		}
		return repo.saveAll(prods);
	}
	
	public Flux<Product> getPr(){
		return repo.findAll();
	}
	
	public Product getProduct(int id) {
		return service.getProduct(id);
	}
	
	
	public Product getProductJpa(int id) {
		return service.getProductJpa(id);
	}
	

	public Product updateJpa(int id, String name) {
		Product prod = service.getProductJpa(id);
		prod.setName(name);
		service.saveJPA(prod);
		return prod;
	}
	
}
