package com.mken.user.service;

import com.mken.user.entity.Employee;

public interface EmployeeService extends UserService {
	
	public Employee createAdminEmployee(Employee user);

}
