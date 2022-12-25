package com.user.service;

import com.user.entity.EmployeeRole;
import com.user.entity.User;

public interface EmployeeService extends UserService {
	
	User createAdminEmployee(User user);
	
	User createAdminEmployee(User user, boolean isSuperUser);
	
	User createEmployee(User user);
	
	EmployeeRole saveEmployeeRole(EmployeeRole empRole);
	
	EmployeeRole findEmployeeRoleById(String rootId);

}
