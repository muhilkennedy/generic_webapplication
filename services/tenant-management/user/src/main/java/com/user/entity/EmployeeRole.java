package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name="EMPLOYEEROLE")
@ClassMetaProperty(code = "ER")
public class EmployeeRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "EMPLOYEEID")
	private String employeeId;
	
	@Column(name = "ROLEID")
	private String roleId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "EMPLOYEEID", nullable = false, insertable = false, updatable = false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}