package com.user.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.server.BaseSession;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.*;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("employee")
@ValidateUserToken
public class EmployeeController {
	
	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse getUserDetails()
	{
		GenericResponse response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(BaseSession.getTenant()).build();
	}
	
}
