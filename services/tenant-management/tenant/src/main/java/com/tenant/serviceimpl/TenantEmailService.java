package com.tenant.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.email.service.EmailService;
import com.base.util.EmailUtil;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;

import freemarker.template.TemplateException;

@Component
public class TenantEmailService {

	@Autowired
	private EmailService emailService;

	public void sendOnbardingMail(Tenant tenant, SubscriptionHistory subscription) {
		try {
			List<Map> inlineMapList = EmailUtil.getBasicInlineImages(tenant.getObjectId(), tenant.getTenantName(),
					tenant.getTenantDetail().getTagLine(), tenant.getTenantDetail().getTenantContact(),
					tenant.getTenantDetail().getTenantEmail(), tenant.getTenantUniqueName());
			Map<String, File> inlineImages = inlineMapList.get(0);
			Map<String, String> map = inlineMapList.get(1);
			map.put("validity", subscription.getRenewedOn().toString() + " - " + subscription.getExpiry().toString());
			emailService.sendMail(tenant.getTenantDetail().getTenantEmail(),
					tenant.getTenantName() + " : NEW ACCOUNT CREATED",
					emailService.constructEmailBody("Tenant-Onboarding", map), inlineImages);
		}catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
