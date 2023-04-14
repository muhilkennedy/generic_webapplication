package com.mken.user.serviceimpl;

import java.util.List;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.mken.base.entity.BaseEntity;
import com.mken.base.util.Log;
import com.mken.base.util.SecurityUtil;
import com.mken.user.emailservice.EmployeeEmailService;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.jpa.repo.EmployeeRepository;
import com.mken.user.r2db.repo.EmployeeR2Repository;
import com.mken.user.service.EmployeeService;
import com.platform.exception.UserNotFoundException;

import reactor.core.publisher.Flux;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private EmployeeR2Repository employeeR2Repo;
	
	@Autowired
	private EmployeeEmailService emailService;

	@Override
	public Object save(BaseEntity obj) {
		return employeeRepo.save((Employee)obj);
	}

	@Override
	public Object saveAndFlush(BaseEntity obj) {
		return employeeRepo.saveAndFlush((Employee)obj);
	}

	@Override
	@Cacheable(value="product", key = "#rootId")
	public Object findById(Long rootId) {
		return employeeRepo.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		employeeRepo.delete((Employee) obj);
		
	}

	@Override
	public List findAll() {
		return employeeRepo.findAll();
	}

	@Override
	public void toggleUserStatus(Long rootId) {
		Employee emp = employeeRepo.findById(rootId).get();
		if(emp != null) {
			emp.setActive(!emp.isActive());
			save(emp);
		}
		else {
			//throw exception
		}
	}
	
	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String encrptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS));
		employee.setPassword(encrptedPassword);
		employee = (Employee) saveAndFlush(employee);
		return employee;
	}

	@Override
	public User login(User user) throws AuthenticationException, UserNotFoundException {
		Employee employee = employeeRepo.findEmployeeByEmailId(user.getEmailId());
		if(employee == null) {
			throw new UserNotFoundException();
		}
		if(!BCrypt.checkpw(user.getPassword(), employee.getPassword())) {
			throw new AuthenticationException("Invalid Password");
		}
		return employee;
	}

	@Override
	public User findByEmailId(String emailId) {
		return employeeRepo.findEmployeeByEmailId(emailId);
	}
	
	@Override
	public Employee createAdminEmployee(Employee employee) {
		String generatedPassword = SecurityUtil.generateRandomPassword();
		Log.user.debug(String.format("Generated password for user {%s} is {%s}", employee.getfName(), generatedPassword));
		String encrptedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS));
		employee.setPassword(encrptedPassword);
		employee.setDesignation("CustomerSupportAdmin");
		employee = (Employee) saveAndFlush(employee);
		// send email
		emailService.sendWelcomeActivationEmail(employee);
		return employee;
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
