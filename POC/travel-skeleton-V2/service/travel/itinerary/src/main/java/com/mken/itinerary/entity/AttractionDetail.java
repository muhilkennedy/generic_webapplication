package com.mken.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mken.base.entity.BaseEntity;
import com.mken.itinerary.model.DestinationAttributes;
import com.mken.itinerary.util.DestinationAttributeConvertor;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "ATTRACTIONDETAIL")
@ClassMetaProperty(code = "AD")
public class AttractionDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "ATTRACTIONID", referencedColumnName = "ROOTID", updatable = false)
	private Attraction attraction;
	
	@Column(name = "ATTRIBUTES")
	@Convert(converter = DestinationAttributeConvertor.class)
	private DestinationAttributes attributes;

	public Attraction getAttraction() {
		return attraction;
	}

	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

	public DestinationAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(DestinationAttributes attributes) {
		this.attributes = attributes;
	}

}
