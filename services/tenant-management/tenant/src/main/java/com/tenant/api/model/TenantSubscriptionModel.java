package com.tenant.api.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class TenantSubscriptionModel {
	
	@NotBlank
	private String renewalDate;

	@NotBlank
	private String expiryDate;

	public String getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public Date fetchExpiryDate() {
		return parseDate(expiryDate);
	}

	public Date fetchRenewalDate() {
		return parseDate(renewalDate);
	}

	private Date parseDate(String date) {
		try {
			return new Date(DATE_FORMAT.parse(date).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
