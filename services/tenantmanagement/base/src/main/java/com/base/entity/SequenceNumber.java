package com.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;

/**
 * @author Muhil Kennedy
 * Custom Sequence Number entity user to manipulate rootId
 */
@Entity
@Table(name="SEQUENCENUMBER")
@ClassMetaProperty(code="SEQ")
public class SequenceNumber extends BaseIdentityEntity {

	@Column(name = "CLASSNAME")
	private String className;
	
	@Column(name = "INCREMENTVALUE")
	private long incrementValue;
	
	@Column(name = "NEXTVALUE")
	private long nextValue;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public long getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(long incrementValue) {
		this.incrementValue = incrementValue;
	}

	public long getNextValue() {
		return nextValue;
	}

	public void setNextValue(long nextValue) {
		this.nextValue = nextValue;
	}

}
