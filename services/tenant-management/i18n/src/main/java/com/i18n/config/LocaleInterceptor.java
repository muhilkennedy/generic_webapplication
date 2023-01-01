package com.i18n.config;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.base.service.BaseSession;

/**
 * @author Muhil
 *
 */
@Component
public class LocaleInterceptor implements WebRequestInterceptor {
	
	@Autowired
	private BaseSession baseSession;

	@Override
	public void preHandle(WebRequest request) throws Exception {
		String lang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		if (baseSession.getTenantInfo() != null &&  StringUtils.isNotBlank(lang)) {
			baseSession.setLocale(lang);
		}
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		// TODO Auto-generated method stub
	}

}
