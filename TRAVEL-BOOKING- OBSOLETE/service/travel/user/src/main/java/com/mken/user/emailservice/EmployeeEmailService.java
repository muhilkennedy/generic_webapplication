package com.mken.user.emailservice;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mken.base.email.service.EmailService;
import com.mken.base.util.Log;
import com.mken.user.entity.User;

import freemarker.template.TemplateException;

/**
 * @author Muhil
 *
 */
@Service
public class EmployeeEmailService {
	
	private static final String DEFAULT_REGISTRATION_FTL = "Registration-Default";
	
	@Autowired
	private EmailService emailService;
	
	public void sendWelcomeActivationEmail(User user, String generatedPassword) {
		Map<String, String> contentMap = new HashedMap<String, String>();
		contentMap.put("userName", String.format("%s %s", user.getfName(), user.getlName()));
		contentMap.put("password", String.valueOf(user.getRootId()));
		contentMap.put("mobile", user.getMobile());
		contentMap.put("email", user.getEmailId());
//		try {
//			emailService.sendMail(user.getEmailId(), String.format("Welcome %s", user.getfName()),
//					emailService.constructEmailBody(DEFAULT_REGISTRATION_FTL, contentMap), null);
//		} catch (IOException | TemplateException e) {
//			Log.user.error("Exception sending mail to user {0} :: error :: {1}", user.getEmailId(), e.getMessage());
//			if(Log.base.isDebugEnabled()) {
//				e.printStackTrace();
//			}
//		}
	}

}
