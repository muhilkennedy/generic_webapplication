package com.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.base.annotation.ClassMetaProperty;

@Entity
@Table(name="FEATURETOGGLE")
@ClassMetaProperty(code="FT")
public class FeatureToggle extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Column(name = "FEATURENAME")
	private String featureName;

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	
	
}
