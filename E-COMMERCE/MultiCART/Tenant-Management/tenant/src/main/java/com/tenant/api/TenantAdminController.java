package com.tenant.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.messages.TenantRequestBody;
import com.tenant.service.TenantService;

import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/tenant")
@ValidateUserToken
public class TenantAdminController {
	
	@Autowired
	private TenantService tenantService;
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request,
			@RequestBody @Valid TenantRequestBody tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.createTenant(tenant);
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/logo", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantLogo(@RequestParam("picture") MultipartFile picture) throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.uploadLogo(BaseUtil.generateFileFromMutipartFile(picture));
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@GetMapping(value = "/alltenants", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> getAllTenants() throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(tenantService.findAllTenants()).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PatchMapping(value = "/toggle", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> toggleTenant(@RequestParam("tenantUniqueName") String uniqueName) throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		tenantService.toggleTenantState(uniqueName);
		return response.setStatus(Response.Status.OK).build();
	}
	

	@UserPermission(values = Permissions.SUPER_USER)
	@GetMapping(value = "/subscriptions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<TenantSubscription> getAllTenantSubscription(@RequestParam("tenantId") Long tenantId) throws IllegalStateException, IOException {
		return tenantService.getAllTenantSubscription(tenantId);
	}

	@UserPermission(values = Permissions.SUPER_USER)
	@PutMapping(value = "/addsubscription", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<TenantSubscription> addSubscription(@RequestParam("tenantId") Long tenantId, @RequestBody @Valid TenantRequestBody tenantBody) throws IllegalStateException, IOException {
		GenericResponse<TenantSubscription> response = new GenericResponse<>();
		BaseEntity uTenant = tenantService.findById(tenantId);
		Assert.notNull(uTenant, "Tenant not Found");
		BaseSession.setTenant(uTenant);//reload tenant context
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantSubscription(tenantBody)).build();
	}
}
