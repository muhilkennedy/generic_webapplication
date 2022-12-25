package com.base.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.annotation.Loggable;
import com.base.annotation.ValidateUserToken;
import com.base.security.Permissions;
import com.base.service.CacheService;
import com.base.util.Log;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("base")
public class BaseController {
	
	@Autowired
	private CacheService cacheService;

	@ValidateUserToken(permissions = {Permissions.SUPER_USER})
	@Loggable(message = "Clear Cache", perf = true)
	@RequestMapping(value = "/cache", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	public Response pingTenant(HttpServletRequest request) {
		Log.base.info("Evicting cache objects");
		cacheService.evictAll();
		return Response.ok().build();
	}
}
