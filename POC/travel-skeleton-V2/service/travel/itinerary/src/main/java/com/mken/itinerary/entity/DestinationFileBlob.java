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
@Table(name = "DESTINATIONFILEBLOB")
@ClassMetaProperty(code = "DSTFB")
public class DestinationFileBlob extends FileBlob {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "DESTINATIONID", referencedColumnName = "ROOTID", updatable = false)
	private Destination destination;

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}
