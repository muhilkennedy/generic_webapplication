package com.user.service;

import com.user.entity.User;

public interface EmployeeService extends UserService {
	
	User createEmployee(User user);

}
