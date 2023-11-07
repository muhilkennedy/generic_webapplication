package com.base.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * @author Muhil Kennedy
 * hibernate sequence auto rootid generated for entities.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DefaultBaseEntity extends MultiTenantEntity implements BaseObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROOTID", updatable = false, nullable = false)
	private long rootId;

	public long getRootId() {
		return rootId;
	}

	public void setRootId(long rootId) {
		this.rootId = rootId;
	}
	
	@Override
	public String getObjectId() {
		return String.valueOf(rootId);
	}
}
