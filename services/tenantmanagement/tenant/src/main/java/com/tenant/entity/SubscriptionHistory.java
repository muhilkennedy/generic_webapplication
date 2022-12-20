package com.tenant.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name="SUBSCRIPTIONHISTORY")
@ClassMetaProperty(code = "SH")
public class SubscriptionHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "RENEWEDON")
	@Temporal(TemporalType.DATE)
	private Date renewedOn;

	@Column(name = "EXPIRY")
	@Temporal(TemporalType.DATE)
	private Date expiry;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TENANTID", insertable = false, updatable = false)
	private Tenant tenant;

	public Date getRenewedOn() {
		return renewedOn;
	}

	public void setRenewedOn(Date renewedOn) {
		this.renewedOn = renewedOn;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

}
