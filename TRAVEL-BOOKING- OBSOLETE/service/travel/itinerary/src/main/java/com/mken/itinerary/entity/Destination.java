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
@Table(name = "DESTINATION")
@ClassMetaProperty(code = "DST")
public class Destination extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "GMAP")
	private String gmap;
	
	@Column(name = "PARENTID")
	private Long parentid;
	
	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "RATING")
	private int rating;
	
	@Column(name = "CUSTOMERRATING")
	private int customerrating;
	
	@Column(name = "PICTURE")
	private String picture;
	
	@OneToOne(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DestinationDetail destinationDetail;
	
	@OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DestinationFileBlob> destinationFileBlobs;
	
	@OneToMany(mappedBy="destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DestinationSeason> destinationSeasons;

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

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public List<DestinationFileBlob> getDestinationFileBlobs() {
		return destinationFileBlobs;
	}

	public void setDestinationFileBlobs(List<DestinationFileBlob> destinationFileBlobs) {
		this.destinationFileBlobs = destinationFileBlobs;
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

	public List<DestinationSeason> getDestinationSeasons() {
		return destinationSeasons;
	}

	public void setDestinationSeasons(List<DestinationSeason> destinationSeasons) {
		this.destinationSeasons = destinationSeasons;
	}

	public DestinationDetail getDestinationDetail() {
		return destinationDetail;
	}

	public void setDestinationDetail(DestinationDetail destinationDetail) {
		this.destinationDetail = destinationDetail;
	}

}
