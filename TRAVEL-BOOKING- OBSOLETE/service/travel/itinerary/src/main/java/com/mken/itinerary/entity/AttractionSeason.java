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
@Table(name = "ATTRACTIONSEASON")
@ClassMetaProperty(code = "ATRSS")
public class AttractionSeason extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "ATTRACTIONID", referencedColumnName = "ROOTID", updatable = false)
	private Attraction attraction;
	
	@Column(name = "MONTH")
	private int month;
	
	@Column(name = "SCALE")
	private int scale;

	public Attraction getAttraction() {
		return attraction;
	}

	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

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

}
