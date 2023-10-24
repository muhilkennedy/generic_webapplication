package com.user.service;

import java.util.List;

import com.platform.user.Permissions;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;

/**
 * @author Muhil
 *
 */
public interface EmployeeService extends UserService {

	List<Employee> findAllCSAUsers();

	boolean doesEmployeeHavePermission(Permissions permission, Long employeeId);

	User createEmployeeInfo(Employee employee, EmployeeInfo info);

}
