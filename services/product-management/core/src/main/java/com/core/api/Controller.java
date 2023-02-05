package com.core.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.service.ProductService;
import com.platform.entity.Tenant;
import com.platform.exception.TenantException;
import com.platform.service.TenantService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("base")
public class Controller {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/ping")
	public ResponseEntity<?> getFile() throws IOException, TenantException {
		Tenant tenant = TenantService.findByUniqueName("devTenant");
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value = "/pingall", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> getFile1() throws IOException {
		return service.getProducts();
	}
	
	@GetMapping(value = "/add", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Product> add() throws IOException {
		return service.save();
	}
	
	@GetMapping(value = "/addALL", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> addAA() throws IOException {
		return service.saveALL();
	}
	
	@GetMapping(value = "/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> eft() throws IOException {
		return service.getPr();
	}

}
