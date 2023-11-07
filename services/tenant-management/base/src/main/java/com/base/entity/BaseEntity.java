package com.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Muhil
 * Custom rootId generated for BaseEnttites
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity extends MultiTenantEntity implements BaseObject,Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="root_Id", strategy = "com.base.generator.RootIdGenerator")
	@GeneratedValue(generator = "root_Id")
	@Column(name = "ROOTID", updatable = false, nullable = false)
	private String rootId;

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	@JsonIgnore
	@Override
	public String getObjectId() {
		return rootId;
	}
}
