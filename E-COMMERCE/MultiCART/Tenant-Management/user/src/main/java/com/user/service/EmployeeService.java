package com.user.service;

import java.util.List;

import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;

/**
 * @author Muhil
 *
 */
public interface EmployeeService extends UserService {

	User createEmployeeInfo(Employee employee, EmployeeInfo info);

	List<Employee> findAllCSAUsers();

}
