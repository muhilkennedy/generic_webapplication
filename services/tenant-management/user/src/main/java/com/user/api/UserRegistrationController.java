package com.user.api;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.messages.GenericResponse;
import com.base.messages.Response;
import com.base.util.JWTUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;
import com.user.messages.UserLoginRequest;
import com.user.messages.UserRequestbody;
import com.user.service.EmployeeService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("authenticate")
public class UserRegistrationController {
	
	@Autowired
	@Qualifier("EmployeeService")
	private EmployeeService empService;
	
	@RequestMapping(value = "/employee/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> registerEmployee(HttpServletRequest request, @RequestBody @Valid UserRequestbody user) {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		employee.setEmailId(user.getEmail());
		employee.setMobile(user.getMobile());
		employee.setPassword(user.getPassword());
		employee.setfName(user.getFirstName());
		employee.setlName(user.getLastName());
		empService.register(employee);
		return response.setStatus(Response.Status.CREATED).setData(employee).build();
	}
	
	@RequestMapping(value = "/employee/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> loginEmployee(HttpServletRequest request, HttpServletResponse httpResponse , @RequestBody @Valid UserLoginRequest user)
			throws AuthenticationException, UserNotFoundException {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		employee.setEmailId(user.getEmail());
		employee.setPassword(user.getPassword());
		employee = (Employee) empService.login(employee);
		return response.setStatus(Response.Status.OK).setData(employee)
				.setDataList(Arrays.asList(JWTUtil.generateToken(employee.getRootId(), JWTUtil.USER_TYPE_EMPLOYEE, false))).build();
	}

}
