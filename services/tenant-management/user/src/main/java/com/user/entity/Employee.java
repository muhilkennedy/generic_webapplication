package com.user.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name="EMPLOYEE")
@ClassMetaProperty(code = "EMP")
public class Employee extends User {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "REPORTSTO")
	private String reportsTo;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EmployeeRole> employeeeRoles;

	public List<EmployeeRole> getEmployeeeRoles() {
		return employeeeRoles;
	}

	public void setEmployeeeRoles(List<EmployeeRole> employeeeRoles) {
		this.employeeeRoles = employeeeRoles;
	}

}
