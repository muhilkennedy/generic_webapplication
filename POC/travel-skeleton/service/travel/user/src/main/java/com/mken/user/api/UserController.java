package com.mken.user.api;

import javax.ws.rs.core.MediaType;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mken.base.util.Constants;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.messages.UserLoginRequest;
import com.mken.user.service.EmployeeService;
import com.platform.exception.UserNotFoundException;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.util.JWTUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private EmployeeService empService;
	
	@RequestMapping(value = "/employee/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> loginEmployee(@RequestBody @Valid UserLoginRequest user, HttpServletResponse httpResponse) throws AuthenticationException, UserNotFoundException {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		employee.setEmailId(user.getEmail());
		employee.setPassword(user.getPassword());
		employee = (Employee) empService.login(employee);
		httpResponse.addHeader(Constants.TOKEN_HEADER,
				JWTUtil.generateToken(String.valueOf(employee.getRootId()), JWTUtil.USER_TYPE_EMPLOYEE, false));
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}
	
	@GetMapping(value = "/fetch", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<?> getAllEmployees() {
		return empService.findAllReactive();
	}

}
