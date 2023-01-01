package com.tenant.tasks;

import java.time.Instant;
import java.util.Date;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.messages.BGWorkException;
import com.base.service.AbstractBGWork;
import com.base.service.BaseSession;
import com.base.util.Log;
import com.tenant.entity.SubscriptionHistory;
import com.tenant.entity.Tenant;
import com.tenant.service.TenantService;
import com.tenant.service.TenantSubscriptionService;

/**
 * @author muhil
 * Schedule job for everyday at 12:05AM
 * Check for tenants renewal or effective activation.
 */
@Component
public class ActivateTenantBackgroundJob extends AbstractBGWork {
	
	@Autowired
	private BaseSession baseSession;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private TenantSubscriptionService subscriptionService;

	@Override
	public void execute() throws BGWorkException {
		Tenant tenant = (Tenant) tenantService.findById(baseSession.getTenantId());
		SubscriptionHistory activeSubscription = subscriptionService.findActiveTenantSubscription();
		if (!tenant.isActive() && activeSubscription != null && activeSubscription.getRenewedOn().before(new Date())) {
			tenant.setActive(true);
			tenantService.save(tenant);
			Log.tenant.info(String.format("Tenant %s is activated on %s", tenant.getTenantUniqueName(),
					Instant.now().toString()));
			// send renewal or activation mail
		}
	}

	@Recurring(id = "Tenant-Activation", cron = "5 0 * * *")
    @Job(name = "Tenant-Activation")
	@Override
	public void scheduleBGJob(JobContext jobContext) {
		scheduleForAllRealms(jobContext);
	}

}