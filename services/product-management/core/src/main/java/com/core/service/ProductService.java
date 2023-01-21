package com.core.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.api.Product;
import com.core.repo.ProductRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;
	
	public Flux<Product> getProducts() {
		List<Product> prods = new ArrayList<>();
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1)).doOnNext(i -> System.out.println(i))
				.map(i -> new Product("item" + i, i));
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
	
}
