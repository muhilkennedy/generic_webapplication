package com.mken.user.entity;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mken.base.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
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
	private String password;

	@Column(name = "PROFILEPIC")
	private String profilepic;

	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timezone;

	@Column(name = "LASTLOGIN")
	private String lastlogin;

	@Column(name = "LASTACTIVE")
	private String lastactive;

	public String getfName() {
		return fname;
	}

	public void setfName(String fName) {
		this.fname = fName;
	}

	public String getlName() {
		return lname;
	}

	public void setlName(String lName) {
		this.lname = lName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailId() {
		return emailid;
	}

	public void setEmailId(String emailId) {
		this.emailid = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilepic;
	}

	public void setProfilePic(String profilePic) {
		this.profilepic = profilePic;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timezone;
	}

	public void setTimeZone(String timeZone) {
		this.timezone = timeZone;
	}

	public String getLastLogin() {
		return lastlogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastlogin = lastLogin;
	}

	public String getLastActive() {
		return lastactive;
	}

	public void setLastActive(String lastActive) {
		this.lastactive = lastActive;
	}

	@PrePersist
	private void prePersist() {
		if (StringUtils.isBlank(timezone)) {
			this.timezone = "IST";
		}
		if (StringUtils.isBlank(locale)) {
			this.locale = "en_US";
		}
	}

}
