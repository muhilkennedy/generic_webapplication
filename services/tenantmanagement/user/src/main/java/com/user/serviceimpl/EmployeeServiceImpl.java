package com.user.serviceimpl;

import java.util.List;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.base.annotation.Cacheble;
import com.base.entity.BaseObject;
import com.base.security.Roles;
import com.base.service.BaseSession;
import com.base.util.BaseUtil;
import com.user.dao.EmployeeRepository;
import com.user.dao.EmployeeRoleRepository;
import com.user.entity.Employee;
import com.user.entity.EmployeeRole;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;
import com.user.service.EmployeeService;
import com.user.service.RolesAndPermissionService;

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
	
	@Autowired
	private EmployeeEmailService emailService;
	
	@Autowired
	private RolesAndPermissionService rolesAndPermissionService;
	
	@Autowired
	private EmployeeRoleRepository employeeRoleRepository;

	@Override
	@Cacheble(update = true)
	public Object save(BaseObject obj) {
		return empRepository.save((Employee) obj);
	}

	@Override
	@Cacheble(update = true)
	public Object saveAndFlush(BaseObject obj) {
		return empRepository.saveAndFlush((Employee) obj);
	}

	@Cacheble
	@Override
	public Object findById(Object rootId) {
		return empRepository.findById(rootId.toString()).get();
	}

	@Override
	@Cacheble(evict = true)
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
	public User createAdminEmployee(User user, boolean isSuperUser) {
		rolesAndPermissionService.loadDefaultRolesForTenant();
		Employee employee = (Employee) user;
		String generatedPassword = BaseUtil.generateRandomPassword();
		String encrptedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(BaseUtil.saltRounds));
		employee.setPassword(encrptedPassword);
		employee.setDesignation("CustomerSupportAdmin");
		employee = (Employee) saveAndFlush(employee);
		EmployeeRole empRole = new EmployeeRole();
		Role adminRole = rolesAndPermissionService.findRoleByUniqueName(
				isSuperUser ? Roles.CUSTOMER_SUPPORT_ADMIN.getRoleUniqueName() : Roles.Admin.getRoleUniqueName());
		empRole.setRoleId(adminRole.getRootId());
		empRole.setEmployeeId(employee.getRootId());
		this.saveEmployeeRole(empRole);
		emailService.sendNewAdminUserEmail(employee, generatedPassword);
		return employee;
	}


	@Override
	public User createAdminEmployee(User user) {
		return createAdminEmployee(user, false);
	}

	@Override
	public List<Employee> findAll() {
		return empRepository.findAll();
	}

	@Override
	@Cacheble(evict = true)
	public void toggleUserStatus(String rootId) {
		Employee employee = (Employee) findById(rootId);
		employee.setActive(!employee.isActive());
		save(employee);
	}

	@Override
	public User createEmployee(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeRole saveEmployeeRole(EmployeeRole empRole) {
		return employeeRoleRepository.saveAndFlush(empRole);
	}

	@Override
	public EmployeeRole findEmployeeRoleById(String rootId) {
		return employeeRoleRepository.findById(rootId).get();
	}

}
