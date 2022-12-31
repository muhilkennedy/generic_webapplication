package com.platform.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.platform.cache.TenantCache;
import com.platform.entity.Tenant;
import com.platform.exception.TenantException;
import com.platform.util.HttpUtil;
import com.platform.util.Log;
import com.platform.util.PropertiesUtil;

/**
 * @author Muhil
 *
 */
public class TenantService {

	private final String findTenantUri = "/tenant/ping";

	private String getTenantUrl(String uri) {
		return PropertiesUtil.getTMFrontDoorUrl().concat(uri);
	}

	public Tenant findByUniqueName(String uniqueName) throws TenantException {
		Tenant tenant = (Tenant) TenantCache.getInstance().cache().get(uniqueName);
		if (tenant == null) {
			HttpClient<Tenant> client = new HttpClient<Tenant>(new Tenant());
			Header header = new BasicHeader(HttpUtil.HEADER_TENANT, uniqueName);
			try {
				tenant = client.get(getTenantUrl(findTenantUri), Arrays.asList(header), null);
				if (tenant == null) {
					throw new TenantException("Tenant not found");
				}
				Log.tenant.info(String.format("Loaded tenant %s from service", tenant.getRootId()));
			} catch (IOException | URISyntaxException e) {
				Log.tenant.error("Tenant fetch exception : " + e.getMessage());
				throw new TenantException(e.getMessage());
			}
		}
		return tenant;
	}

}
