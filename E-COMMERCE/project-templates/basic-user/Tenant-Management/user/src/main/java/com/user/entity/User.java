package com.user.entity;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.base.entity.MultiTenantEntity;
import com.base.security.EncryptionConvertor;
import com.base.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

/**
 * @author Muhil Kennedy
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "UNIQUENAME", updatable = false)
	private String uniquename;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "EMAILID")
	private String emailid;

	@JsonIgnore
	@Column(name = "PASSWORD")
	@Convert(converter = EncryptionConvertor.class)
	private String password;

	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timezone;

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@PrePersist
	private synchronized void preProcess() throws SQLException {
		if (StringUtils.isBlank(timezone)) {
			this.timezone = "IST";
		}
		if (StringUtils.isBlank(locale)) {
			this.locale = "en_US";
		}
		if (StringUtils.isEmpty(uniquename)) {
			try {
				generateUniqueName();
			} catch (SQLException e) {
				Log.user.error("Exception generating unique name for user {}", e);
				throw e;
			}
		}
	}

	protected void generateUniqueName() throws SQLException {
		// NO-OP
	}

}
