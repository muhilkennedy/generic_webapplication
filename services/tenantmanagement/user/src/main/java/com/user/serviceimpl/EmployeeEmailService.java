package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.email.service.EmailService;
import com.base.service.BaseSession;
import com.base.util.EmailUtil;
import com.tenant.entity.Tenant;
import com.user.entity.Employee;

import freemarker.template.TemplateException;

@Component
public class EmployeeEmailService {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BaseSession baseSession;
	
	public void sendNewAdminUserEmail(Employee user, String generatedPassword) {
		try {
			Tenant tenant = (Tenant)baseSession.getTenantInfo();
			List<Map> inlineMapList = EmailUtil.getBasicInlineImages(tenant.getObjectId(), tenant.getTenantName(),
					tenant.getTenantDetail().getTagLine(), tenant.getTenantDetail().getTenantContact(),
					tenant.getTenantDetail().getTenantEmail(), tenant.getTenantUniqueName());
			Map<String, File> inlineImages = inlineMapList.get(0);
			Map<String, String> map = inlineMapList.get(1);
			map.put("employeeName", user.getfName());
			map.put("employeeId", user.getObjectId());
			map.put("password", generatedPassword);
			emailService.sendMail(user.getEmailId(), tenant.getTenantDetail().getTenantEmail(),
					tenant.getTenantName() + " : NEW USER CREATED",
					emailService.constructEmailBody("Admin-User", map), inlineImages, null);
		}catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
