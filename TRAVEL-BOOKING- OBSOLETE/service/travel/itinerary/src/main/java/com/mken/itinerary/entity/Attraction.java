package com.mken.itinerary.entity;

import java.util.List;

import com.mken.base.entity.BaseEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "ATTRACTION")
@ClassMetaProperty(code = "ATR")
public class Attraction extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "DESTINATIONID")
	private Long destinationid;

	@Column(name = "NAME")
	private String name;

	@Column(name = "GMAP")
	private String gmap;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "RATING")
	private int rating;

	@Column(name = "CUSTOMERRATING")
	private int customerrating;

	@Column(name = "PICTURE")
	private String picture;
	
	@OneToOne(mappedBy = "attraction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private AttractionDetail attractionDetail;

	@OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AttractionFileBlob> attractionFileBlobs;

	@OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AttractionSeason> attractionSeasons;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGmap() {
		return gmap;
	}

	public void setGmap(String gmap) {
		this.gmap = gmap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getCustomerrating() {
		return customerrating;
	}

	public void setCustomerrating(int customerrating) {
		this.customerrating = customerrating;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<AttractionFileBlob> getAttractionFileBlobs() {
		return attractionFileBlobs;
	}

	public void setAttractionFileBlobs(List<AttractionFileBlob> attractionFileBlobs) {
		this.attractionFileBlobs = attractionFileBlobs;
	}

	public List<AttractionSeason> getAttractionSeasons() {
		return attractionSeasons;
	}

	public void setAttractionSeasons(List<AttractionSeason> attractionSeasons) {
		this.attractionSeasons = attractionSeasons;
	}

	public Long getDestinationid() {
		return destinationid;
	}

	public void setDestinationid(Long destinationid) {
		this.destinationid = destinationid;
	}

	public AttractionDetail getAttractionDetail() {
		return attractionDetail;
	}

	public void setAttractionDetail(AttractionDetail attractionDetail) {
		this.attractionDetail = attractionDetail;
	}

}
