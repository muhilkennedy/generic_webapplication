package com.mken.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mken.base.email.service.BaseDaoService;
import com.mken.base.entity.BaseEntity;
import com.mken.base.util.CacheUtil;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.jpa.repo.EmployeeRepository;
import com.mken.user.r2db.repo.EmployeeR2Repository;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 * handle all dao operations here.
 *
 */
@Service
@Qualifier("EmployeeDao")
public class EmployeeDaoService implements BaseDaoService {
	
	@Autowired
	private EmployeeR2Repository employeeR2Repo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Override
	@CachePut(value = CacheUtil.EMPLOYEE_CACHE_NAME, key="#obj.rootId")
	public BaseEntity save(BaseEntity obj) {
		return employeeRepo.save((Employee)obj);
	}

	@Override
	@CachePut(value = CacheUtil.EMPLOYEE_CACHE_NAME, key="#obj.rootId")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return employeeRepo.saveAndFlush((Employee)obj);
	}

	@Override
	@Cacheable(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return employeeRepo.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#obj.rootId")
	public void delete(BaseEntity obj) {
		employeeRepo.delete((Employee) obj);
		
	}

	@Override
	public List findAll() {
		return employeeRepo.findAll();
	}
	
	@Override
	public void deleteById(Long rootId) {
		employeeRepo.deleteById(rootId);
	}
	
	public User findByEmailId(String emailId) {
		return employeeRepo.findEmployeeByEmailId(emailId);
	}
	
	/****************************
	 * Reactive service impls.
	****************************/
	
	@Override
	public Flux<Employee> findAllReactive() {
		Flux<Employee> flux = employeeR2Repo.findAll();
		return flux;
	}

}
