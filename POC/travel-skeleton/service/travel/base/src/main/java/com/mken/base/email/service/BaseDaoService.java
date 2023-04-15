package com.mken.base.email.service;

import java.util.List;

import com.mken.base.entity.BaseEntity;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface BaseDaoService {
	
	public BaseEntity save(BaseEntity obj);

	public BaseEntity saveAndFlush(BaseEntity obj);

	public BaseEntity findById(Long rootId);
	
	public void delete(BaseEntity obj);
	
	public List<?> findAll();
	
	public Flux<?> findAllReactive();

}
