package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;

@Entity
@Table(name="CUSTOMER")
@ClassMetaProperty(code = "USR")
public class Customer extends User {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ORIGIN")
	private String origin;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
