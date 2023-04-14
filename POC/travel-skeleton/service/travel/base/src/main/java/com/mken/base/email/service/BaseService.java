package com.mken.base.email.service;

import java.util.List;

import com.mken.base.entity.BaseEntity;

import reactor.core.publisher.Flux;

public interface BaseService {
	
	public Object save(BaseEntity obj);

	public Object saveAndFlush(BaseEntity obj);

	public Object findById(Long rootId);
	
	public void delete(BaseEntity obj);
	
	public List<?> findAll();
	
	public Flux<?> findAllReactive();

}
