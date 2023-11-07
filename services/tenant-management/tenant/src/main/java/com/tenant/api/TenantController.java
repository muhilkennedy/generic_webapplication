package com.tenant.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.Loggable;
import com.base.messages.GenericResponse;
import com.base.messages.Response;
import com.base.service.BaseSession;
import com.tenant.entity.Tenant;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("tenant")
public class TenantController {

	@Autowired
	private BaseSession baseSession;

	@Loggable(message = "tenant pinged", perf = true)
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> pingTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		response.setStatus(Response.Status.OK).setData((Tenant) baseSession.getTenantInfo());
		return response;
	}

}
