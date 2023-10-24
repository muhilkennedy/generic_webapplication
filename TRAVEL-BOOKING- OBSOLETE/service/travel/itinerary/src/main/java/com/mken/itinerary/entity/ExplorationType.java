package com.mken.itinerary.entity;

import com.mken.base.entity.BaseEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EXPLORATIONTYPE")
@ClassMetaProperty(code = "ET")
public class ExplorationType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "UNIQUENAME")
	private String uniqueName;
	
	@Column(name = "FILESTOREID")
	private Long fileStoreId;

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public Long getFileStoreId() {
		return fileStoreId;
	}

	public void setFileStoreId(Long fileStoreId) {
		this.fileStoreId = fileStoreId;
	}
	
}
