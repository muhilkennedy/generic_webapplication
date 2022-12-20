package com.user.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.StringUtils;

import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "FNAME")
	private String fName;

	@Column(name = "LNAME")
	private String lName;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "EMAILID")
	private String emailId;

	@JsonIgnore
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PROFILEPIC")
	private Blob profilePic;

	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timeZone;

	@Column(name = "LASTLOGIN")
	private String lastLogin;

	@Column(name = "LASTACTIVE")
	private String lastActive;

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Blob getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Blob profilePic) {
		this.profilePic = profilePic;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@PrePersist
	private void prePersist() {
		if (StringUtils.isBlank(timeZone)) {
			this.timeZone = "IST";
		}
		if (StringUtils.isBlank(locale)) {
			this.locale = "en_US";
		}
	}

}
