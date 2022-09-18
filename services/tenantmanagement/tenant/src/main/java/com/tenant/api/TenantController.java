package com.tenant.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.Loggable;
import com.base.service.BaseSession;
import com.base.util.GenericResponse;
import com.base.util.Response;
import com.tenant.api.model.TenantModel;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantOrigin;
import com.tenant.serviceimpl.TenantServiceImpl;

@RestController
@RequestMapping("tenant")
public class TenantController {
	
	@Autowired
	private TenantServiceImpl tenantService;
	
	@Autowired
	BaseSession baseSession;
	
	@Loggable(message = "ping", perf=true)
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> pingTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		response.setData((Tenant)baseSession.getTenantInfo());
		return response;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request, @RequestBody TenantModel tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = (Tenant)tenantService.saveAndFlush(tenant.getTenant());
		return response.setStatus(Response.Status.OK).setData(newTenant).build();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> updateTenant(HttpServletRequest request, @RequestBody Tenant tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant actualTenant = (Tenant) baseSession.getTenantInfo();
		actualTenant.setTenantName(tenant.getTenantName());
		tenantService.saveAndFlush(actualTenant);
		return response.setStatus(Response.Status.OK).setData(actualTenant).build();
	}
	
	/*
	 * deletion on realm not possible - impl purge to delete all dependencies first 
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> deleteTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = (Tenant) baseSession.getTenantInfo();
		tenantService.delete(newTenant);
		response.setData(newTenant);
		return response;
	}*/
	
	@RequestMapping(value = "/addorgin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<TenantOrigin> addTenantOrigin(HttpServletRequest request,
			@RequestBody TenantOrigin tenantOrigin) {
		GenericResponse<TenantOrigin> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantOrigin(tenantOrigin)).build();
	}

}
