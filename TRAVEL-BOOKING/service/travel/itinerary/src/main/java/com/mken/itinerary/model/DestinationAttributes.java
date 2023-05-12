package com.mken.itinerary.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Muhil
 *
 */
public class DestinationAttributes implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> reasonsForVisit;
	private List<String> forYou;
	private List<String> notForYou;

	public List<String> getReasonsForVisit() {
		return reasonsForVisit;
	}

	public void setReasonsForVisit(List<String> reasonsForVisit) {
		this.reasonsForVisit = reasonsForVisit;
	}

	public List<String> getForYou() {
		return forYou;
	}

	public void setForYou(List<String> forYou) {
		this.forYou = forYou;
	}

	public List<String> getNotForYou() {
		return notForYou;
	}

	public void setNotForYou(List<String> notForYou) {
		this.notForYou = notForYou;
	}

}
