package com.mken.user.service;

import org.springframework.stereotype.Service;

import com.mken.user.entity.Employee;

/**
 * @author Muhil
 *
 */
@Service
public interface EmployeeService extends UserService {
	
	public Employee createAdminEmployee(Employee user);

}
