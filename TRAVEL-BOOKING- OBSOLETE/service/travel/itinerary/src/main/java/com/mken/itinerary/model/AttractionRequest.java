package com.mken.itinerary.model;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class AttractionRequest {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String gmap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGmap() {
		return gmap;
	}

	public void setGmap(String gmap) {
		this.gmap = gmap;
	}

}
