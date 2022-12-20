package com.user.serviceimpl;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.base.entity.BaseObject;
import com.base.service.BaseSession;
import com.base.util.BaseUtil;
import com.base.util.RedisCacheUtil;
import com.user.dao.EmployeeRepository;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;
import com.user.service.EmployeeService;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private BaseSession baseSession;

	@Autowired
	private EmployeeRepository empRepository;

	@Override
	@CachePut(value = RedisCacheUtil.USER_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Object save(BaseObject obj) {
		return empRepository.save((Employee) obj);
	}

	@Override
	@CachePut(value = RedisCacheUtil.USER_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public Object saveAndFlush(BaseObject obj) {
		return empRepository.saveAndFlush((Employee) obj);
	}

	@Override
	@Cacheable(value = RedisCacheUtil.USER_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR, unless="#result==null")
	public Object findById(Object rootId) {
		return empRepository.findById(rootId.toString()).get();
	}

	@Override
	@CacheEvict(value = RedisCacheUtil.USER_CACHE, keyGenerator = RedisCacheUtil.REDIS_KEY_GENERATOR)
	public void delete(BaseObject obj) {
		empRepository.delete((Employee) obj);
	}

	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String encrptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(BaseUtil.saltRounds));
		employee.setPassword(encrptedPassword);
		employee = (Employee) saveAndFlush(employee);
		return employee;
	}
	
	

	@Override
	public void toggleUserStatus() {
		User user = (User) baseSession.getUserInfo();
		user.setActive(!user.isActive());
		save(user);
	}

	@Override
	public User login(User user) throws UserNotFoundException, AuthenticationException {
		Employee employee = empRepository.findEmployeeByEmailId(user.getEmailId());
		if(employee == null) {
			throw new UserNotFoundException();
		}
		if(!BCrypt.checkpw(user.getPassword(), employee.getPassword())) {
			throw new AuthenticationException("Incorrect Password");
		}
		return employee;
	}

	@Override
	public User findByEmailId(String emailId) {
		return empRepository.findEmployeeByEmailId(emailId);
	}

	@Override
	public User findByEmailIdForTenant(String emailId, String tenantId) {
		return empRepository.findEmployeeByEmailIdByTenant(tenantId, emailId);
	}

	@Override
	public User createEmployee(User user) {
		Employee employee = (Employee) user;
		user.setPassword(BaseUtil.generateRandomPassword());
		String encrptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(BaseUtil.saltRounds));
		employee.setPassword(encrptedPassword);
		employee = (Employee) saveAndFlush(employee);
		//send email java
		return employee;
	}

}
