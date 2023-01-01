package com.tenant.tasks;

import java.sql.Date;
import java.time.Instant;

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
 * Schedule job for everyday at 12:00AM
 * Check for expiring and expired tenants.
 */
@Component
public class ExpireTenantBackgroundJob extends AbstractBGWork {
	
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
		java.util.Date currDate = new java.util.Date();
		Date date = new Date(currDate.getTime());
		if (tenant.isActive() && activeSubscription != null
				&& date.getTime() >= activeSubscription.getExpiry().getTime()) {
			tenant.setActive(false);
			tenantService.save(tenant);
			Log.tenant.info(String.format("Tenant %s is deactivated on %s", tenant.getTenantUniqueName(),
					Instant.now().toString()));
			// send deactivation mail
		}
		//check for tenant upcoming expiry
	}

	@Recurring(id = "Tenant-Expiry", cron = "0 0 * * *")
    @Job(name = "Tenant-Expiry")
	@Override
	public void scheduleBGJob(JobContext jobContext) {
		scheduleForAllRealms(jobContext);
	}

}
