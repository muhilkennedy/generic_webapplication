package com.mken.user.api;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mken.base.service.ReCaptchaService;
import com.mken.base.session.BaseSession;
import com.mken.user.emailservice.EmployeeEmailService;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.messages.UserRequestbody;
import com.mken.user.service.EmployeeService;
import com.platform.annotations.ValidateUserToken;
import com.platform.exception.ReCaptchaException;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("employee")
@ValidateUserToken
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private ReCaptchaService captchaService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> createEmployee(@RequestBody @Valid UserRequestbody user) throws ReCaptchaException{
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		employee.setEmailId(user.getEmail());
		employee.setfName(user.getFirstName());
		employee.setlName(user.getLastName());
		employee.setMobile(user.getMobile());
		employee.setPassword(user.getPassword());
        if(!captchaService.verify(user.getRecaptchaResponse())) {
        	throw new ReCaptchaException("ReCaptcha Validation Failed! Try again.");
        }
		empService.createAdminEmployee(employee);
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}
	
	@Autowired
	EmployeeEmailService emailService;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> getUserDetails()
	{
		GenericResponse<User> response = new GenericResponse<>();
		emailService.sendWelcomeActivationEmail((User)BaseSession.getUser());
		return response.setStatus(Response.Status.OK).setData((User)BaseSession.getUser()).build();
	}
	
	@GetMapping(value = "/fetch", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Employee> getAllEmployees() {
		return (Flux<Employee>) empService.findAllReactive();
	}
	
	@PatchMapping(value = "/togglestatus", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<User> toggleEmployeeStatus(@RequestParam("rootId") Long rootId) {
		GenericResponse<User> response = new GenericResponse<>();
		if(rootId != null) {
			empService.toggleUserStatus(rootId);
		}
		else {
			empService.toggleUserStatus(BaseSession.getUser().getRootId());
		}
		return response.setStatus(Response.Status.OK).setData((User)BaseSession.getUser()).build();
	}
	
}
