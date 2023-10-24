package com.mken.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mken.base.entity.BaseEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "DESTINATIONSEASON")
@ClassMetaProperty(code = "DSTSS")
public class DestinationSeason extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "DESTINATIONID", referencedColumnName = "ROOTID", updatable = false)
	private Destination destination;
	
	@Column(name = "MONTH")
	private int month;
	
	@Column(name = "SCALE")
	private int scale;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
}
