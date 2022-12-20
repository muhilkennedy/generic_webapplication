package com.base.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.entity.BaseObject;
import com.base.messages.BGWorkException;
import com.base.util.DatabaseUtil;
import com.base.util.Log;

/**
 * @author muhil
 * common bg funtionalities are implemented in this abstract class.
 */
@Component
public abstract class AbstractBGWork implements BGWork {
	
	@Autowired
	private BaseSession baseSession;
	
	private void setUpSession(BaseObject tenant) {
		baseSession.setTenantInfo(tenant);
		baseSession.setTenantId(tenant.getObjectId());
	}
	
	private void setUpSession(String tenantId) {
		baseSession.setTenantId(tenantId);
	}
	
	protected void tearDownSession() {
		baseSession.clear();
	}
	
	private List<String> getAllRealms(){
		List<String> tenantIds = new ArrayList<String>();
		try(Connection con=DatabaseUtil.getConnectionInstance()){
			PreparedStatement pstmt = con.prepareStatement("select rootId from tenant");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String tenantId = rs.getString(1);
				tenantIds.add(tenantId);
			}
		} catch (Exception e) {
			Log.base.error("AbstractBGWork : " + e.getMessage());
			throw new RuntimeException(e);
		}
		return tenantIds;
	}
	
	/**
	 * schedule job based on annotation
    /* @Recurring(id = "my-recurring-job", cron = "0 0/15 * * *")
     * @Job(name = "My recurring job")
	 */
	protected void scheduleForAllRealms(JobContext jobContext) {
		List<String> tenantIds = getAllRealms();
		JobDashboardProgressBar progressBar = jobContext.progressBar(tenantIds.size());
		tenantIds.stream().forEach(tenantId -> {
			try {
				jobContext.logger()
				.info(String.format("Started Proccesing %s job for tenant %s", jobContext.getJobName(), tenantId));
				Log.base.info(
						String.format("Started Proccesing %s job for tenant %s", jobContext.getJobName(), tenantId));
				setUpSession(tenantId);
				this.execute();
				Log.base.info(String.format("Completed %s job for tenant %s", jobContext.getJobName(), tenantId));
			} catch (BGWorkException e) {
				Log.base.error("AbstractBGWork : " + String.format("Error Proccesing %s job for tenant %s : error : %s",
						jobContext.getJobName(), tenantId, e.getMessage()));
				jobContext.logger().error(String.format("Error Proccesing %s job for tenant %s : error : %s",
						jobContext.getJobName(), tenantId, e.getMessage()));
				throw new RuntimeException(e);
			} finally {
				tearDownSession();
				progressBar.increaseByOne();
				jobContext.logger()
						.info(String.format("Completed %s job for tenant %s", jobContext.getJobName(), tenantId));
			}
		});
	}
	
	protected void scheduleForRealm(String id) {
		setUpSession(id);
		try {
			Log.base.info("Executing job for tenant : " + id);
			this.execute();
			Log.base.info("Executing job for tenant : " + id);
		} catch (BGWorkException e) {
			Log.base.error("AbstractBGWork : " + e.getMessage());
			throw new RuntimeException(e);
		}finally {
			tearDownSession();
		}
		
	}	


}
