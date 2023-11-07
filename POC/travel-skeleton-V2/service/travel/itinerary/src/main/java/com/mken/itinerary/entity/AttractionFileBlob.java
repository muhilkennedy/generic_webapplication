package com.mken.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mken.base.entity.FileBlob;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "ATTRACTIONFILEBLOB")
@ClassMetaProperty(code = "ATRFB")
public class AttractionFileBlob extends FileBlob {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ATTRACTIONID", referencedColumnName = "ROOTID", updatable = false)
	private Attraction attraction;

	public Attraction getAttraction() {
		return attraction;
	}

	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

}
