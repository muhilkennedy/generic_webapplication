package com.base.service;

import org.jobrunr.jobs.context.JobContext;

import com.base.messages.BGWorkException;

/**
 * @author muhil
 * leverages spring runr for background jobs.
 */
public interface BGWork {

	/**
	 * actual BG logic execution
	 */
	public void execute() throws BGWorkException;
	
	/**
	 * determine whether to execute job for all tenant/specific tenant.
	 * override and call related methods
	 */
	public void scheduleBGJob(JobContext jobContext);
}
