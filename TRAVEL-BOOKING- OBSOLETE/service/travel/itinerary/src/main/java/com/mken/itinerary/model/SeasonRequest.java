package com.mken.itinerary.model;

import jakarta.validation.constraints.NotNull;

/**
 * @author Muhil
 *
 */
public class SeasonRequest {
	
	@NotNull
	private int month;
	
	@NotNull
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

}
