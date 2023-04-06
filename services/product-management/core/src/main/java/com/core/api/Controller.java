package com.core.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.QueryParam;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.service.ProductService;
import com.platform.entity.Tenant;
import com.platform.exception.TenantException;
import com.platform.service.TenantService;
import com.platform.util.DocxUtil;

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
	
		InputStream is = getClass().getResourceAsStream("/gcs/NMB.docx");
		File tempFile = File.createTempFile("NMB", ".docx");
	    FileUtils.copyInputStreamToFile(is, tempFile);
		DocxUtil.split(tempFile.getPath(), 200000);
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
	
	@GetMapping(value = "/getone")
	public Mono<Product> ef1t(@RequestParam("tt") String rr) throws IOException {
		//System.out.println(service.getProduct(2146939423).getName());
		Product pro1d = service.getProduct(2146939423);
		System.out.println("after initial fetch");
		//Product prod = service.update(2146939423, rr).block();
//		Product prod = service.getProduct(2146939423);
//		prod.setName(rr);
//		service.save(prod);
		service.update(2146939423, rr);
		return Mono.just(service.getProduct(2146939423)); //service.getProduct(2146939423);
		
//		service.getProduct(2146939423);
//		service.updateJpa(2146939423, rr);
//		return service.getProductJpa(2146939423);
	}
	
	@GetMapping(value = "/getone1")
	public Product ef1t1(@RequestParam("tt") String rr) throws IOException {
		service.getProductJpa(2146939423);
		System.out.println("after initial fetch");
		service.updateJpa(2146939423, rr);
		return service.getProductJpa(2146939423);
	}

}
