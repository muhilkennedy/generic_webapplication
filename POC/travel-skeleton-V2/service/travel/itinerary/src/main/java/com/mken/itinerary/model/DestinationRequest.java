package com.mken.itinerary.model;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class DestinationRequest {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String gmap;
	
	private int rating;
	
	private Long parentDestinationId;

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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Long getParentDestinationId() {
		return parentDestinationId;
	}

	public void setParentDestinationId(Long parentDestinationId) {
		this.parentDestinationId = parentDestinationId;
	}

}
