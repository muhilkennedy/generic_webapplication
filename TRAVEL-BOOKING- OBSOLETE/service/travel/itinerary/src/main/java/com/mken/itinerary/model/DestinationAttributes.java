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
	private List<String> safety;
	private List<String> freeAdvice;

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

	public List<String> getSafety() {
		return safety;
	}

	public void setSafety(List<String> safety) {
		this.safety = safety;
	}

	public List<String> getFreeAdvice() {
		return freeAdvice;
	}

	public void setFreeAdvice(List<String> freeAdvice) {
		this.freeAdvice = freeAdvice;
	}

}
