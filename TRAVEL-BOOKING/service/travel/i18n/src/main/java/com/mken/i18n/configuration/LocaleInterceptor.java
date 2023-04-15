package com.mken.i18n.configuration;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.mken.base.session.BaseSession;
import com.mken.base.util.Log;


/**
 * @author Muhil
 *
 */
@Component
public class LocaleInterceptor implements WebRequestInterceptor {

	@Override
	public void preHandle(WebRequest request) throws Exception {
		String lang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		if (BaseSession.getUser() != null &&  StringUtils.isNotBlank(lang)) {
			BaseSession.setLocale(lang);
		}
		else {
			Log.i18n.warn("No Locale information found in request");
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
