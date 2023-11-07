package com.tenant.api;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.tenant.api.model.TenantDetailsBody;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.api.model.TenantSubscriptionModel;
import com.tenant.api.model.TenantsResponse;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantOrigin;
import com.tenant.exception.TenantException;
import com.tenant.service.TenantService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("tenantresource")
@UserAuthValidation
public class TenantResourceController {
	
	@Autowired
	private TenantService tenantService;

	@Autowired
	private BaseSession baseSession;
	
	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request,
			@RequestBody @Valid TenantRequestBody tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = (Tenant) tenantService.createTenant(tenant);
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/alltenants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<TenantsResponse> getAllTenants(HttpServletRequest request) throws SQLException, Exception {
		GenericResponse<TenantsResponse> response = new GenericResponse<>();
		response.setStatus(Response.Status.OK).setDataList(tenantService.getAllTenants()).build();
		return response;
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/toggleTenantStatus", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> toggleTenant(HttpServletRequest request,
			@RequestParam(value = "tenantUniqueName", required = false) @Valid @NotBlank String tenantUniqueName)
			throws TenantException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		if (StringUtils.isAllBlank(tenantUniqueName)) {
			tenantService.toggleTenantStatus();
		} else {
			tenantService.toggleTenantStatus(tenantUniqueName);
		}
		return response.setStatus(Response.Status.OK)
				.setData(tenantService.findTenantByUniqueName(StringUtils.isAllBlank(tenantUniqueName)
						? ((Tenant) baseSession.getTenantInfo()).getTenantUniqueName()
						: tenantUniqueName))
				.build();
	}

	/*
	 * deletion of realm not possible - impl purge to delete all dependencies first
	 * 
	 * @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces =
	 * MediaType.APPLICATION_JSON) public GenericResponse<Tenant>
	 * deleteTenant(HttpServletRequest request) { GenericResponse<Tenant> response =
	 * new GenericResponse<>(); Tenant newTenant = (Tenant)
	 * baseSession.getTenantInfo(); tenantService.delete(newTenant);
	 * response.setData(newTenant); return response; }
	 */

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/addTenantDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Tenant> addTenantDetail(HttpServletRequest request,
			@RequestBody @Valid @NotNull TenantDetailsBody tenantDetails) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantDetails(tenantDetails)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/addorgin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<TenantOrigin> addTenantOrigin(HttpServletRequest request,
			@RequestBody TenantOrigin tenantOrigin) {
		GenericResponse<TenantOrigin> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantOrigin(tenantOrigin)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<SubscriptionHistory> getSubscriptions(
			@RequestParam(value = "tenantUniqueName", required = false) String tenantUniqueName,
			HttpServletRequest request) {
		GenericResponse<SubscriptionHistory> response = new GenericResponse<>();
		if (StringUtils.isNotBlank(tenantUniqueName)) {
			tenantService.reEvaluateSessionForTenant(tenantUniqueName);
		}
		List<SubscriptionHistory> subs = tenantService.getTenantHistory();
		response.setStatus(Response.Status.OK).setDataList(subs);
		return response;
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@RequestMapping(value = "/subscriptions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public GenericResponse<SubscriptionHistory> postSubscriptions(
			@RequestParam(value = "tenantUniqueName", required = false) String tenantUniqueName,
			@RequestBody @Valid TenantSubscriptionModel subscriptionModel) {
		GenericResponse<SubscriptionHistory> response = new GenericResponse<>();
		if (StringUtils.isNotBlank(tenantUniqueName)) {
			tenantService.reEvaluateSessionForTenant(tenantUniqueName);
		}
		tenantService.updateTenantExpiry(subscriptionModel);
		List<SubscriptionHistory> subs = tenantService.getTenantHistory();
		response.setStatus(Response.Status.OK).setDataList(subs);
		return response;
	}

}
