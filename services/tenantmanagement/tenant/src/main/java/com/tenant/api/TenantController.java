package com.tenant.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.Loggable;
import com.base.service.BaseSession;
import com.base.util.GenericResponse;
import com.base.util.Response;
import com.sun.istack.NotNull;
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;
import com.tenant.serviceimpl.TenantServiceImpl;

@RestController
@RequestMapping("tenant")
public class TenantController {
	
	@Autowired
	private TenantServiceImpl tenantService;
	
	@Autowired
	private BaseSession baseSession;
	
	@Loggable(message = "ping")
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> pingTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		response.setStatus(Response.Status.OK).setData((Tenant)baseSession.getTenantInfo());
		return response;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request, @RequestBody @Valid TenantRequestBody tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = (Tenant)tenantService.createTenant(tenant);
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}
	
	//refactor this handler to update specific fields
	/*@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> updateTenant(HttpServletRequest request, @RequestBody @Valid Tenant tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant actualTenant = (Tenant) baseSession.getTenantInfo();
		actualTenant.setTenantName(tenant.getTenantName());
		tenantService.saveAndFlush(actualTenant);
		return response.setStatus(Response.Status.OK).setData(actualTenant).build();
	}*/
	
	@RequestMapping(value = "/toggleTenantStatus", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> toggleTenant(HttpServletRequest request, @RequestParam(value="tenantUniqueName", required = false) @Valid @NotBlank String tenantUniqueName) throws TenantException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		if(StringUtils.isAllBlank(tenantUniqueName)) {
			tenantService.toggleTenantStatus();
		}
		else {
			tenantService.toggleTenantStatus(tenantUniqueName);
		}
		return response.setStatus(Response.Status.OK).setData((Tenant) baseSession.getTenantInfo()).build();
	}

	/*
	 * deletion of realm not possible - impl purge to delete all dependencies first 
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> deleteTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = (Tenant) baseSession.getTenantInfo();
		tenantService.delete(newTenant);
		response.setData(newTenant);
		return response;
	}*/
	
	@RequestMapping(value = "/addTenantDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> addTenantDetail(HttpServletRequest request,
			@RequestBody @Valid @NotNull TenantDetailsBody tenantDetails) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantDetails(tenantDetails)).build();
	}
	
	@RequestMapping(value = "/addorgin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<TenantOrigin> addTenantOrigin(HttpServletRequest request,
			@RequestBody TenantOrigin tenantOrigin) {
		GenericResponse<TenantOrigin> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantOrigin(tenantOrigin)).build();
	}

}
