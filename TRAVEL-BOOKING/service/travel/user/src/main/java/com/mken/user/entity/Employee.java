package com.mken.user.entity;


import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
@ClassMetaProperty(code = "EMP")
public class Employee extends User {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "REPORTSTO")
	private String reportsto;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getReportsto() {
		return reportsto;
	}

	public void setReportsto(String reportsto) {
		this.reportsto = reportsto;
	}

}
