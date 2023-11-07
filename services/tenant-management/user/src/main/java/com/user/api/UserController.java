package com.user.api;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.UserAuthValidation;
import com.base.annotation.UserPermission;
import com.base.messages.GenericResponse;
import com.base.messages.Response;
import com.base.security.Permissions;
import com.base.service.BaseSession;
import com.base.util.JWTUtil;
import com.tenant.entity.Tenant;
import com.tenant.exception.TenantException;
import com.tenant.service.TenantService;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;
import com.user.messages.UserLoginRequest;
import com.user.messages.UserRequestbody;
import com.user.service.EmployeeService;

@RestController
@RequestMapping("user")
@UserAuthValidation
public class UserController {
	
	@Autowired
	private BaseSession baseSession;
	
	@Autowired
	@Qualifier("EmployeeService")
	private EmployeeService empService;
	
	@Autowired
	private TenantService tenantService;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> getUser(HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData((User) baseSession.getUserInfo()).build();
	}
	
	// Use this endpoint only for tenant-management
	@UserPermission(values = {Permissions.SUPER_USER})
	@RequestMapping(value = "/employee/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> createAdminEmployee(HttpServletRequest request,
			@RequestParam(value = "tenantUniqueName", required = false) String tenantUniqueName,
			@RequestBody @Valid UserRequestbody user) throws AuthenticationException, UserNotFoundException {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		employee.setEmailId(user.getEmail());
		employee.setfName(user.getFirstName());
		employee.setlName(user.getLastName());
		employee.setMobile(user.getMobile());
		if (StringUtils.isNotBlank(tenantUniqueName)) {
			Tenant expectedTenant = tenantService.findTenantByUniqueName(tenantUniqueName);
			baseSession.setTenantInfo(expectedTenant);
			tenantService.reEvaluateTenantSession();
			empService.createAdminEmployee(employee);
		}
		else {
			empService.createAdminEmployee(employee, true);
		}
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}
	
	@UserPermission(values = {Permissions.ADMIN, Permissions.EDIT_USERS})
	@RequestMapping(value = "/createemployee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> createEmployee(HttpServletRequest request,
			@RequestParam(value = "tenantUniqueName", required = false) String tenantUniqueName,
			@RequestBody @Valid UserRequestbody user) {
		GenericResponse<User> response = new GenericResponse<>();
		//impl
		return null;
	}
	
	//impl get by user id
	@UserPermission(values = {Permissions.SUPER_USER, Permissions.ADMIN, Permissions.MANAGE_USERS})
	@RequestMapping(value = "/employee/fetch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> getAllUser(HttpServletRequest request) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(empService.findAll()).build();
	}
	
	@UserPermission(values = {Permissions.SUPER_USER, Permissions.ADMIN, Permissions.EDIT_USERS})
	@RequestMapping(value = "/employee/toggleuserstatus", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> toggleTenant(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) @Valid @NotBlank String userRootId)
			throws TenantException {
		GenericResponse<User> response = new GenericResponse<>();
		if (StringUtils.isAllBlank(userRootId)) {
			empService.toggleUserStatus();
		} else {
			empService.toggleUserStatus(userRootId);
		}
		return response.setStatus(Response.Status.OK).setData((User) empService.findById(
				StringUtils.isAllBlank(userRootId) ? ((User) baseSession.getUserInfo()).getObjectId() : userRootId))
				.build();
	}

}
